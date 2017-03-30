package com.ble.lib.x2.proto;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bt_data_req.proto

public final class BtDataReq {
    private BtDataReq() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    /**
     * Protobuf enum {@code DATA_TYPE}
     */
    public enum DATA_TYPE
            implements com.google.protobuf.Internal.EnumLite {
        /**
         * <code>FILE_REQ = 1;</code>
         */
        FILE_REQ(0, 1),
        /**
         * <code>FILE_RSP = 2;</code>
         */
        FILE_RSP(1, 2),
        /**
         * <code>COMMAND_REQ = 3;</code>
         */
        COMMAND_REQ(2, 3),
        /**
         * <code>COMMAND_RSP = 4;</code>
         */
        COMMAND_RSP(3, 4),
        /**
         * <code>ALGDATA_REQ = 5;</code>
         */
        ALGDATA_REQ(4, 5),
        /**
         * <code>ALGDATA_RSP = 6;</code>
         */
        ALGDATA_RSP(5, 6),
        /**
         * <code>DATA_REQ = 7;</code>
         */
        DATA_REQ(6, 7),
        /**
         * <code>ACK = 8;</code>
         */
        ACK(7, 8),
        /**
         * <code>TIME = 9;</code>
         */
        TIME(8, 9),
        /**
         * <code>PID = 10;</code>
         */
        PID(9, 10),
        /**
         * <code>MAC = 11;</code>
         */
        MAC(10, 11),
        /**
         * <code>ALARM = 12;</code>
         */
        ALARM(11, 12),
        /**
         * <code>WEATHER_INFO = 13;</code>
         */
        WEATHER_INFO(12, 13),
        /**
         * <code>GET_DATA_RSP = 14;</code>
         */
        GET_DATA_RSP(13, 14),
        /**
         * <code>DEVIDE_INFO = 15;</code>
         */
        DEVIDE_INFO(14, 15),
        /**
         * <code>CALENDAR = 16;</code>
         */
        CALENDAR(15, 16),
        /**
         * <code>USER_INFO = 17;</code>
         */
        USER_INFO(16, 17),
        /**
         * <code>CARD_ORDER = 18;</code>
         */
        CARD_ORDER(17, 18),
        /**
         * <code>WEATHER_SETTING = 19;</code>
         */
        WEATHER_SETTING(18, 19),
        /**
         * <code>NOTIFY = 20;</code>
         */
        NOTIFY(19, 20),
        /**
         * <code>QUIET_TIME = 21;</code>
         */
        QUIET_TIME(20, 21),
        /**
         * <code>VIBRATE = 22;</code>
         */
        VIBRATE(21, 22),
        /**
         * <code>READ_FLASH_REQ = 23;</code>
         */
        READ_FLASH_REQ(22, 23),
        /**
         * <code>READ_FLASH_RSP = 24;</code>
         */
        READ_FLASH_RSP(23, 24),
        /**
         * <code>CFG_REQ = 25;</code>
         */
        CFG_REQ(24, 25),
        /**
         * <code>CFG_RSP = 26;</code>
         */
        CFG_RSP(25, 26),
        /**
         * <code>SMART_PUSH = 27;</code>
         */
        SMART_PUSH(26, 27),
        /**
         * <code>USER_TARGET = 28;</code>
         */
        USER_TARGET(27, 28),
        /**
         * <code>WATCH_SKIN = 29;</code>
         */
        WATCH_SKIN(28, 29),
        /**
         * <code>NX2_WATCH_SKIN_SWITCH = 30;</code>
         */
        NX2_WATCH_SKIN_SWITCH(29, 30),
        /**
         * <code>NX2_WATCH_SKIN_RSP = 31;</code>
         */
        NX2_WATCH_SKIN_RSP(30, 31),
        /**
         * <code>NX2_CARD_ON_OFF = 32;</code>
         */
        NX2_CARD_ON_OFF(31, 32),
        /**
         * <code>WALK_REMIND = 33;</code>
         */
        WALK_REMIND(32, 33),
        /**
         * <code>NX2_WATCH_SKIN_CURRENT = 34;</code>
         */
        NX2_WATCH_SKIN_CURRENT(33, 34),
        /**
         * <code>RAW_DATA_REQ = 35;</code>
         */
        RAW_DATA_REQ(34, 35),
        /**
         * <code>RAW_DATA_RSP = 36;</code>
         */
        RAW_DATA_RSP(35, 36),
        /**
         * <code>APP_MANUAL_SPORT = 37;</code>
         */
        APP_MANUAL_SPORT(36, 37),
        /**
         * <code>NX2_WEATHER = 38;</code>
         */
        NX2_WEATHER(37, 38),
        /**
         * <code>HEART_RATE = 39;</code>
         */
        HEART_RATE(38, 39),
        /**
         * <code>SUMMARY = 40;</code>
         */
        SUMMARY(39, 40),;

        /**
         * <code>FILE_REQ = 1;</code>
         */
        public static final int FILE_REQ_VALUE = 1;
        /**
         * <code>FILE_RSP = 2;</code>
         */
        public static final int FILE_RSP_VALUE = 2;
        /**
         * <code>COMMAND_REQ = 3;</code>
         */
        public static final int COMMAND_REQ_VALUE = 3;
        /**
         * <code>COMMAND_RSP = 4;</code>
         */
        public static final int COMMAND_RSP_VALUE = 4;
        /**
         * <code>ALGDATA_REQ = 5;</code>
         */
        public static final int ALGDATA_REQ_VALUE = 5;
        /**
         * <code>ALGDATA_RSP = 6;</code>
         */
        public static final int ALGDATA_RSP_VALUE = 6;
        /**
         * <code>DATA_REQ = 7;</code>
         */
        public static final int DATA_REQ_VALUE = 7;
        /**
         * <code>ACK = 8;</code>
         */
        public static final int ACK_VALUE = 8;
        /**
         * <code>TIME = 9;</code>
         */
        public static final int TIME_VALUE = 9;
        /**
         * <code>PID = 10;</code>
         */
        public static final int PID_VALUE = 10;
        /**
         * <code>MAC = 11;</code>
         */
        public static final int MAC_VALUE = 11;
        /**
         * <code>ALARM = 12;</code>
         */
        public static final int ALARM_VALUE = 12;
        /**
         * <code>WEATHER_INFO = 13;</code>
         */
        public static final int WEATHER_INFO_VALUE = 13;
        /**
         * <code>GET_DATA_RSP = 14;</code>
         */
        public static final int GET_DATA_RSP_VALUE = 14;
        /**
         * <code>DEVIDE_INFO = 15;</code>
         */
        public static final int DEVIDE_INFO_VALUE = 15;
        /**
         * <code>CALENDAR = 16;</code>
         */
        public static final int CALENDAR_VALUE = 16;
        /**
         * <code>USER_INFO = 17;</code>
         */
        public static final int USER_INFO_VALUE = 17;
        /**
         * <code>CARD_ORDER = 18;</code>
         */
        public static final int CARD_ORDER_VALUE = 18;
        /**
         * <code>WEATHER_SETTING = 19;</code>
         */
        public static final int WEATHER_SETTING_VALUE = 19;
        /**
         * <code>NOTIFY = 20;</code>
         */
        public static final int NOTIFY_VALUE = 20;
        /**
         * <code>QUIET_TIME = 21;</code>
         */
        public static final int QUIET_TIME_VALUE = 21;
        /**
         * <code>VIBRATE = 22;</code>
         */
        public static final int VIBRATE_VALUE = 22;
        /**
         * <code>READ_FLASH_REQ = 23;</code>
         */
        public static final int READ_FLASH_REQ_VALUE = 23;
        /**
         * <code>READ_FLASH_RSP = 24;</code>
         */
        public static final int READ_FLASH_RSP_VALUE = 24;
        /**
         * <code>CFG_REQ = 25;</code>
         */
        public static final int CFG_REQ_VALUE = 25;
        /**
         * <code>CFG_RSP = 26;</code>
         */
        public static final int CFG_RSP_VALUE = 26;
        /**
         * <code>SMART_PUSH = 27;</code>
         */
        public static final int SMART_PUSH_VALUE = 27;
        /**
         * <code>USER_TARGET = 28;</code>
         */
        public static final int USER_TARGET_VALUE = 28;
        /**
         * <code>WATCH_SKIN = 29;</code>
         */
        public static final int WATCH_SKIN_VALUE = 29;
        /**
         * <code>NX2_WATCH_SKIN_SWITCH = 30;</code>
         */
        public static final int NX2_WATCH_SKIN_SWITCH_VALUE = 30;
        /**
         * <code>NX2_WATCH_SKIN_RSP = 31;</code>
         */
        public static final int NX2_WATCH_SKIN_RSP_VALUE = 31;
        /**
         * <code>NX2_CARD_ON_OFF = 32;</code>
         */
        public static final int NX2_CARD_ON_OFF_VALUE = 32;
        /**
         * <code>WALK_REMIND = 33;</code>
         */
        public static final int WALK_REMIND_VALUE = 33;
        /**
         * <code>NX2_WATCH_SKIN_CURRENT = 34;</code>
         */
        public static final int NX2_WATCH_SKIN_CURRENT_VALUE = 34;
        /**
         * <code>RAW_DATA_REQ = 35;</code>
         */
        public static final int RAW_DATA_REQ_VALUE = 35;
        /**
         * <code>RAW_DATA_RSP = 36;</code>
         */
        public static final int RAW_DATA_RSP_VALUE = 36;
        /**
         * <code>APP_MANUAL_SPORT = 37;</code>
         */
        public static final int APP_MANUAL_SPORT_VALUE = 37;
        /**
         * <code>NX2_WEATHER = 38;</code>
         */
        public static final int NX2_WEATHER_VALUE = 38;
        /**
         * <code>HEART_RATE = 39;</code>
         */
        public static final int HEART_RATE_VALUE = 39;
        /**
         * <code>SUMMARY = 40;</code>
         */
        public static final int SUMMARY_VALUE = 40;


        public final int getNumber() {
            return value;
        }

        public static DATA_TYPE valueOf(int value) {
            switch (value) {
                case 1:
                    return FILE_REQ;
                case 2:
                    return FILE_RSP;
                case 3:
                    return COMMAND_REQ;
                case 4:
                    return COMMAND_RSP;
                case 5:
                    return ALGDATA_REQ;
                case 6:
                    return ALGDATA_RSP;
                case 7:
                    return DATA_REQ;
                case 8:
                    return ACK;
                case 9:
                    return TIME;
                case 10:
                    return PID;
                case 11:
                    return MAC;
                case 12:
                    return ALARM;
                case 13:
                    return WEATHER_INFO;
                case 14:
                    return GET_DATA_RSP;
                case 15:
                    return DEVIDE_INFO;
                case 16:
                    return CALENDAR;
                case 17:
                    return USER_INFO;
                case 18:
                    return CARD_ORDER;
                case 19:
                    return WEATHER_SETTING;
                case 20:
                    return NOTIFY;
                case 21:
                    return QUIET_TIME;
                case 22:
                    return VIBRATE;
                case 23:
                    return READ_FLASH_REQ;
                case 24:
                    return READ_FLASH_RSP;
                case 25:
                    return CFG_REQ;
                case 26:
                    return CFG_RSP;
                case 27:
                    return SMART_PUSH;
                case 28:
                    return USER_TARGET;
                case 29:
                    return WATCH_SKIN;
                case 30:
                    return NX2_WATCH_SKIN_SWITCH;
                case 31:
                    return NX2_WATCH_SKIN_RSP;
                case 32:
                    return NX2_CARD_ON_OFF;
                case 33:
                    return WALK_REMIND;
                case 34:
                    return NX2_WATCH_SKIN_CURRENT;
                case 35:
                    return RAW_DATA_REQ;
                case 36:
                    return RAW_DATA_RSP;
                case 37:
                    return APP_MANUAL_SPORT;
                case 38:
                    return NX2_WEATHER;
                case 39:
                    return HEART_RATE;
                case 40:
                    return SUMMARY;
                default:
                    return null;
            }
        }

        public static com.google.protobuf.Internal.EnumLiteMap<DATA_TYPE>
        internalGetValueMap() {
            return internalValueMap;
        }

        private static com.google.protobuf.Internal.EnumLiteMap<DATA_TYPE>
                internalValueMap =
                new com.google.protobuf.Internal.EnumLiteMap<DATA_TYPE>() {
                    public DATA_TYPE findValueByNumber(int number) {
                        return DATA_TYPE.valueOf(number);
                    }
                };

        private final int value;

        DATA_TYPE(int index, int value) {
            this.value = value;
        }

        // @@protoc_insertion_point(enum_scope:DATA_TYPE)
    }

    public interface bt_data_reqOrBuilder extends
            // @@protoc_insertion_point(interface_extends:bt_data_req)
            com.google.protobuf.MessageLiteOrBuilder {

        /**
         * <code>optional .DATA_TYPE data_type = 1;</code>
         */
        boolean hasDataType();

        /**
         * <code>optional .DATA_TYPE data_type = 1;</code>
         */
        DATA_TYPE getDataType();
    }

    /**
     * Protobuf type {@code bt_data_req}
     */
    public static final class bt_data_req extends
            com.google.protobuf.GeneratedMessageLite implements
            // @@protoc_insertion_point(message_implements:bt_data_req)
            bt_data_reqOrBuilder {
        // Use bt_data_req.newBuilder() to construct.
        private bt_data_req(com.google.protobuf.GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private bt_data_req(boolean noInit) {
            this.unknownFields = com.google.protobuf.ByteString.EMPTY;
        }

        private static final bt_data_req defaultInstance;

        public static bt_data_req getDefaultInstance() {
            return defaultInstance;
        }

        public bt_data_req getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.ByteString unknownFields;

        private bt_data_req(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.ByteString.Output unknownFieldsOutput =
                    com.google.protobuf.ByteString.newOutput();
            com.google.protobuf.CodedOutputStream unknownFieldsCodedOutput =
                    com.google.protobuf.CodedOutputStream.newInstance(
                            unknownFieldsOutput);
            try {
                boolean done = false;
                while (!done) {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        default: {
                            if (!parseUnknownField(input, unknownFieldsCodedOutput,
                                    extensionRegistry, tag)) {
                                done = true;
                            }
                            break;
                        }
                        case 8: {
                            int rawValue = input.readEnum();
                            DATA_TYPE value = DATA_TYPE.valueOf(rawValue);
                            if (value == null) {
                                unknownFieldsCodedOutput.writeRawVarint32(tag);
                                unknownFieldsCodedOutput.writeRawVarint32(rawValue);
                            } else {
                                bitField0_ |= 0x00000001;
                                dataType_ = value;
                            }
                            break;
                        }
                    }
                }
            } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                throw e.setUnfinishedMessage(this);
            } catch (java.io.IOException e) {
                throw new com.google.protobuf.InvalidProtocolBufferException(
                        e.getMessage()).setUnfinishedMessage(this);
            } finally {
                try {
                    unknownFieldsCodedOutput.flush();
                } catch (java.io.IOException e) {
                    // Should not happen
                } finally {
                    unknownFields = unknownFieldsOutput.toByteString();
                }
                makeExtensionsImmutable();
            }
        }

        public static com.google.protobuf.Parser<bt_data_req> PARSER =
                new com.google.protobuf.AbstractParser<bt_data_req>() {
                    public bt_data_req parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new bt_data_req(input, extensionRegistry);
                    }
                };

        @Override
        public com.google.protobuf.Parser<bt_data_req> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        public static final int DATA_TYPE_FIELD_NUMBER = 1;
        private DATA_TYPE dataType_;

        /**
         * <code>optional .DATA_TYPE data_type = 1;</code>
         */
        public boolean hasDataType() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>optional .DATA_TYPE data_type = 1;</code>
         */
        public DATA_TYPE getDataType() {
            return dataType_;
        }

        private void initFields() {
            dataType_ = DATA_TYPE.FILE_REQ;
        }

        private byte memoizedIsInitialized = -1;

        public final boolean isInitialized() {
            byte isInitialized = memoizedIsInitialized;
            if (isInitialized == 1) return true;
            if (isInitialized == 0) return false;

            memoizedIsInitialized = 1;
            return true;
        }

        public void writeTo(com.google.protobuf.CodedOutputStream output)
                throws java.io.IOException {
            getSerializedSize();
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                output.writeEnum(1, dataType_.getNumber());
            }
            output.writeRawBytes(unknownFields);
        }

        private int memoizedSerializedSize = -1;

        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            if (((bitField0_ & 0x00000001) == 0x00000001)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeEnumSize(1, dataType_.getNumber());
            }
            size += unknownFields.size();
            memoizedSerializedSize = size;
            return size;
        }

        private static final long serialVersionUID = 0L;

        @Override
        protected Object writeReplace()
                throws java.io.ObjectStreamException {
            return super.writeReplace();
        }

        public static bt_data_req parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_data_req parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_data_req parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_data_req parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_data_req parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_data_req parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static bt_data_req parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }

        public static bt_data_req parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static bt_data_req parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_data_req parseFrom(
                com.google.protobuf.CodedInputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(bt_data_req prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        /**
         * Protobuf type {@code bt_data_req}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageLite.Builder<
                        bt_data_req, Builder>
                implements
                // @@protoc_insertion_point(builder_implements:bt_data_req)
                bt_data_reqOrBuilder {
            // Construct using BtDataReq.bt_data_req.newBuilder()
            private Builder() {
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                dataType_ = DATA_TYPE.FILE_REQ;
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public bt_data_req getDefaultInstanceForType() {
                return bt_data_req.getDefaultInstance();
            }

            public bt_data_req build() {
                bt_data_req result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public bt_data_req buildPartial() {
                bt_data_req result = new bt_data_req(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.dataType_ = dataType_;
                result.bitField0_ = to_bitField0_;
                return result;
            }

            public Builder mergeFrom(bt_data_req other) {
                if (other == bt_data_req.getDefaultInstance()) return this;
                if (other.hasDataType()) {
                    setDataType(other.getDataType());
                }
                setUnknownFields(
                        getUnknownFields().concat(other.unknownFields));
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(
                    com.google.protobuf.CodedInputStream input,
                    com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                    throws java.io.IOException {
                bt_data_req parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (bt_data_req) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private DATA_TYPE dataType_ = DATA_TYPE.FILE_REQ;

            /**
             * <code>optional .DATA_TYPE data_type = 1;</code>
             */
            public boolean hasDataType() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>optional .DATA_TYPE data_type = 1;</code>
             */
            public DATA_TYPE getDataType() {
                return dataType_;
            }

            /**
             * <code>optional .DATA_TYPE data_type = 1;</code>
             */
            public Builder setDataType(DATA_TYPE value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                bitField0_ |= 0x00000001;
                dataType_ = value;

                return this;
            }

            /**
             * <code>optional .DATA_TYPE data_type = 1;</code>
             */
            public Builder clearDataType() {
                bitField0_ = (bitField0_ & ~0x00000001);
                dataType_ = DATA_TYPE.FILE_REQ;

                return this;
            }

            // @@protoc_insertion_point(builder_scope:bt_data_req)
        }

        static {
            defaultInstance = new bt_data_req(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:bt_data_req)
    }


    static {
    }

    // @@protoc_insertion_point(outer_class_scope)
}