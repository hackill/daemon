package com.ble.lib.x;

import android.util.Log;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import cn.ginshell.bong.BongApp;
import cn.ginshell.bong.common.SportType;
import cn.ginshell.bong.db.DBHeart;
import cn.ginshell.bong.db.DBRawData;
import cn.ginshell.bong.db.DBWaitingBlock;
import cn.ginshell.bong.sync.WaitingBlockHelper;
import cn.ginshell.bong.utils.LogUtil;


/**
 * @author hackill
 * @date 1/22/16.
 */
public class BongDecode {
    private static final String TAG = "BongDecode";

    public static final TimeZone CHINA_TIMEZONE = TimeZone.getTimeZone("GMT+08:00");

    public static List<DBRawData> decodeRawDataAndHeartDate(List<byte[]> rawData, List<byte[]> heartData) {
        if (rawData == null || heartData == null)
            return null;
        List<DBRawData> rawDataList = new ArrayList<>(rawData.size() * 2);

        for (byte[] raw : rawData) {
            if (!decodeRawData(rawDataList, raw)) {
                Log.e(TAG, "decodeRawData failure ");
            }
        }

        List<DBHeart> heartList = new ArrayList<>(heartData.size());

        for (byte[] h : heartData) {
            decodeHeartData(rawDataList, heartList, h);
        }

        BongApp.getBongComponent().getHeartDao().insertOrReplaceInTx(heartList);

        return rawDataList;

    }

    /**
     * 必须在所有 raw data 解析完成后 执行此方法
     *
     * @param heartList
     * @param rawHeart
     */
    private static void decodeHeartData(List<DBRawData> rawDataList, List<DBHeart> heartList, byte[] rawHeart) {
        if (rawHeart == null || rawHeart.length != 8 || rawDataList == null) {
            Log.e(TAG, "decodeHeartData failure");
            return;
        }
        int year, month, day, hour, minute, heartRate, second, manual;


        ByteBuffer bb = ByteBuffer.wrap(rawHeart);

        //1
        int tmp = bb.getInt();

        month = (tmp >> 28) & 0x0000000f;
        day = (tmp >> 23) & 0x0000001f;
        hour = (tmp >> 18) & 0x0000001f;
        minute = (tmp >> 12) & 0x0000003f;
        year = ((tmp >> 4) & 0x0000003f) + 2000;


        //2
        tmp = bb.getInt();
        second = (tmp >> 16) & 0x000000ff;
        manual = (tmp >> 8) & 0x000000ff;

        heartRate = (tmp >> 24) & 0x000000ff;


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(CHINA_TIMEZONE);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        long timestamp = calendar.getTimeInMillis() / 1000;


        insertHeartRateData(rawDataList, TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(timestamp)), heartRate);

        DBHeart heart = new DBHeart();

        heart.setManual(manual == 1);
        heart.setHeart((short) heartRate);
        heart.setTimestamp(timestamp);

        heartList.add(heart);
    }


    private static boolean insertHeartRateData(List<DBRawData> rawDataList, long timestamp, int rate) {
        if (rawDataList == null) {
            return false;
        }

        for (DBRawData rd : rawDataList) {
            if (rd.getTimestamp() == timestamp) {
                rd.setHeartrate(rate);
                return true;
            }
        }


        return true;
    }

    /**
     * 举例：F1-00-00-02-AE-27-B1-00-AE-08-01-00-00-00-00-00
     * <p>
     * F1-00-00-02：02 睡眠
     * AE-27-B1-00：开始时间，解析参考步数解析规则
     * AE-08-01-00：结束时间，解析参考步数解析规则
     * 00-00-00-00：预留
     *
     * @param waitingList
     * @return
     */

    public static boolean decodeSportTypeData(List<byte[]> waitingList) {
        if (waitingList == null || waitingList.size() == 0) {
            Log.e(TAG, "decodeSportTypeData: ..waitingList is null or size = 0 ");
            return false;
        }

        int year, month, day, hour, minute, type;
        long start_time, end_time;
        int index, total;
        ArrayList<DBWaitingBlock> waitingBlockList = new ArrayList<>(waitingList.size());

        for (byte[] data : waitingList) {


            ByteBuffer bb = ByteBuffer.wrap(data);

            //1
            int tmp = bb.getInt();
            type = tmp & 0xff;

            DBWaitingBlock dbWaitingBlock = new DBWaitingBlock();

            dbWaitingBlock.setPresent_type(type);

            //2
            tmp = bb.getInt();

            year = ((tmp >> 4) & 0x0000003F) + 2000;

            month = (tmp >> 28) & 0x0000000F;

            day = (tmp >> 23) & 0x0000001F;

            hour = (tmp >> 18) & 0x0000001F;

            minute = (tmp >> 12) & 0x0000003F;

            start_time = getTime(year, month, day, hour, minute);


            //3
            tmp = bb.getInt();

            year = ((tmp >> 4) & 0x0000003F) + 2000;

            month = (tmp >> 28) & 0x0000000F;

            day = (tmp >> 23) & 0x0000001F;

            hour = (tmp >> 18) & 0x0000001F;

            minute = (tmp >> 12) & 0x0000003F;


            end_time = getTime(year, month, day, hour, minute);


            //4
            tmp = bb.getInt();

            index = (tmp >> 24) & 0x000000ff;
            total = (tmp >> 16) & 0x000000ff;


            dbWaitingBlock.setStart_time(start_time);
            dbWaitingBlock.setEnd_time(end_time);

            //过滤掉不合理的数据
            if (end_time <= start_time
                    || end_time - start_time > TimeUnit.DAYS.toSeconds(1)
                    || end_time - start_time < TimeUnit.MINUTES.toSeconds(3)) {
                Log.e(TAG, "decodeSportTypeData: error data " + LogUtil.formatWaitingBlock(dbWaitingBlock));
                continue;
            }

            if (type == SportType.Run.getTypeInt()
                    || type == SportType.Bicycle.getTypeInt()
                    || type == SportType.Fitness.getTypeInt()) {
                waitingBlockList.add(dbWaitingBlock);
                Log.i(TAG, "decodeSportTypeData: " + LogUtil.formatWaitingBlock(dbWaitingBlock));
            } else if (type == SportType.RunTrainer_1.getTypeInt()
                    || type == SportType.RunTrainer_2.getTypeInt()
                    || type == SportType.RunTrainer_3.getTypeInt()
                    || type == SportType.RunTrainer_4.getTypeInt()
                    || type == SportType.RunTrainer_5.getTypeInt()
                    || type == SportType.RunTrainer_6.getTypeInt()
                    || type == SportType.RunTrainer_7.getTypeInt()
                    || type == SportType.RunTrainer_8.getTypeInt()) {
                Log.i(TAG, "decodeSportTypeData: ...run trainer type = " + type + ", block = " + LogUtil.formatWaitingBlock(dbWaitingBlock));
            } else {
                Log.v(TAG, "ignore decodeSportTypeData: " + LogUtil.formatWaitingBlock(dbWaitingBlock));
            }
        }

        //insertToDb

        WaitingBlockHelper.insertWaitingBlocks(waitingBlockList);

        return true;
    }

    private static long getTime(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(CHINA_TIMEZONE);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long timestamp = calendar.getTimeInMillis() / 1000 - 60;
        return timestamp;
    }

    /**
     * typedef struct {
     * unsigned int timestamp; second
     * short quiet;
     * short alert;
     * short move;
     * short walk;
     * short run;
     * short swim;
     * short bongflag;
     * short chargeflag;
     * short steps;
     * int amp;
     * } RAWDATA;
     *
     * @param rawDataList
     * @param raw
     */
    private static boolean decodeRawData(List<DBRawData> rawDataList, byte[] raw) {
        if (raw == null || raw.length != 16 || rawDataList == null) {
            Log.e(TAG, "decodeRawData wrong raw data");
            return false;
        }

        DBRawData rd0 = new DBRawData();
        DBRawData rd1 = new DBRawData();
        int tmp;

        int year, month, day, hour, minute;

        short quiet0, quiet1;
        short alert0, alert1;
        short move0, move1;
        short walk0, walk1;
        short run0, run1;
        short swim0, swim1;
        short bongflag0, bongflag1;
        short chargeflag0, chargeflag1;
        short steps0, steps1;
        int amp0, amp1;
        int step_high0, step_high1;

        ByteBuffer bb = ByteBuffer.wrap(raw);

        //1
        tmp = bb.getInt();

        month = (tmp >> 28) & 0x0000000f;
        day = (tmp >> 23) & 0x0000001f;
        hour = (tmp >> 18) & 0x0000001f;
        minute = (tmp >> 12) & 0x0000003f;

        step_high0 = (tmp >> 4) & 0x00000080;
        step_high1 = (tmp >> 3) & 0x00000080;

        year = ((tmp >> 4) & 0x0000003f) + 2000;


        chargeflag0 = (short) ((tmp >> 3) & 0x00000001);
        bongflag0 = (short) ((tmp >> 2) & 0x00000001);
        chargeflag1 = (short) ((tmp >> 1) & 0x00000001);
        bongflag1 = (short) ((tmp) & 0x00000001);

        //2
        tmp = bb.getInt();

        quiet0 = (short) ((tmp >> 27) & 0x0000001f);
        alert0 = (short) ((tmp >> 22) & 0x0000001f);
        move0 = (short) ((tmp >> 17) & 0x0000001f);
        walk0 = (short) ((tmp >> 12) & 0x0000001f);
        run0 = (short) ((tmp >> 7) & 0x0000001f);
        steps0 = (short) ((tmp) & 0x0000007f);

        //3
        tmp = bb.getInt();

        quiet1 = (short) ((tmp >> 27) & 0x0000001f);
        alert1 = (short) ((tmp >> 22) & 0x0000001f);
        move1 = (short) ((tmp >> 17) & 0x0000001f);
        walk1 = (short) ((tmp >> 12) & 0x0000001f);
        run1 = (short) ((tmp >> 7) & 0x0000001f);
        steps1 = (short) ((tmp) & 0x0000007f);

        //4
        tmp = bb.getInt();

        swim0 = (short) ((tmp >> 28) & 0x0000000f);
        amp0 = (tmp >> 16) & 0x00000fff;
        swim1 = (short) ((tmp >> 12) & 0x0000000f);
        amp1 = (tmp) & 0x00000fff;


        if (step_high0 != 0) {
            steps0 += 0x80;
        }

        if (step_high1 != 0) {
            steps1 += 0x80;
        }


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(CHINA_TIMEZONE);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

//
//        Log.i(TAG, "decodeRawData raw = [" + String.format("%08X", tmp) + "], year = [" + year + "]" +
//                ", month = [" + month + "], day = [" + day + "], hour = [" + hour + "], minute = [" + minute + "], date = [" + calendar.getTime() + "]");

        long timestamp = calendar.getTimeInMillis() / 1000 - 60;

        rd0.setTimestamp(timestamp);
        rd0.setQuiet(quiet0);
        rd0.setAlert(alert0);
        rd0.setMove(move0);
        rd0.setWalk(walk0);
        rd0.setRun(run0);
        rd0.setSteps(steps0);
        rd0.setSwim(swim0);
        rd0.setAmp(amp0);
        rd0.setChargeflag(chargeflag0);
        rd0.setBongflag(bongflag0);


        timestamp += 60;
        rd1.setTimestamp(timestamp);
        rd1.setQuiet(quiet1);
        rd1.setAlert(alert1);
        rd1.setMove(move1);
        rd1.setWalk(walk1);
        rd1.setRun(run1);
        rd1.setSteps(steps1);
        rd1.setSwim(swim1);
        rd1.setAmp(amp1);
        rd1.setChargeflag(chargeflag1);
        rd1.setBongflag(bongflag1);


        rawDataList.add(rd0);
        rawDataList.add(rd1);

        return true;
    }


    /**
     * 解析版本
     *
     * @param hexStr
     * @return success return version string, else return null;
     */
    public static String decodeVersion(String hexStr) {
        try {
            //hexStr的截取
            int fVer1 = (Integer.parseInt(hexStr.substring(12, 14), 16));
            int fVer2 = (Integer.parseInt(hexStr.substring(14, 16), 16));
            return fVer1 + "." + fVer2;
        } catch (Exception e) {
            Log.w(TAG, "decodeVersion: ", e);
        }
        return null;
    }
}
