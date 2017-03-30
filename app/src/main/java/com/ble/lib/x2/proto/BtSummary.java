package com.ble.lib.x2.proto;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bt_summary.proto

public final class BtSummary {
    private BtSummary() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public interface bt_summaryOrBuilder extends
            // @@protoc_insertion_point(interface_extends:bt_summary)
            com.google.protobuf.MessageLiteOrBuilder {

        /**
         * <code>optional uint32 step = 1;</code>
         */
        boolean hasStep();

        /**
         * <code>optional uint32 step = 1;</code>
         */
        int getStep();

        /**
         * <code>optional uint32 distance = 2;</code>
         * <p/>
         * <pre>
         * meter
         * </pre>
         */
        boolean hasDistance();

        /**
         * <code>optional uint32 distance = 2;</code>
         * <p/>
         * <pre>
         * meter
         * </pre>
         */
        int getDistance();

        /**
         * <code>optional uint32 calorie = 3;</code>
         * <p/>
         * <pre>
         * calorie KJ
         * </pre>
         */
        boolean hasCalorie();

        /**
         * <code>optional uint32 calorie = 3;</code>
         * <p/>
         * <pre>
         * calorie KJ
         * </pre>
         */
        int getCalorie();
    }

    /**
     * Protobuf type {@code bt_summary}
     */
    public static final class bt_summary extends
            com.google.protobuf.GeneratedMessageLite implements
            // @@protoc_insertion_point(message_implements:bt_summary)
            bt_summaryOrBuilder {
        // Use bt_summary.newBuilder() to construct.
        private bt_summary(com.google.protobuf.GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private bt_summary(boolean noInit) {
            this.unknownFields = com.google.protobuf.ByteString.EMPTY;
        }

        private static final bt_summary defaultInstance;

        public static bt_summary getDefaultInstance() {
            return defaultInstance;
        }

        public bt_summary getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.ByteString unknownFields;

        private bt_summary(
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
                            bitField0_ |= 0x00000001;
                            step_ = input.readUInt32();
                            break;
                        }
                        case 16: {
                            bitField0_ |= 0x00000002;
                            distance_ = input.readUInt32();
                            break;
                        }
                        case 24: {
                            bitField0_ |= 0x00000004;
                            calorie_ = input.readUInt32();
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

        public static com.google.protobuf.Parser<bt_summary> PARSER =
                new com.google.protobuf.AbstractParser<bt_summary>() {
                    public bt_summary parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new bt_summary(input, extensionRegistry);
                    }
                };

        @Override
        public com.google.protobuf.Parser<bt_summary> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        public static final int STEP_FIELD_NUMBER = 1;
        private int step_;

        /**
         * <code>optional uint32 step = 1;</code>
         */
        public boolean hasStep() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>optional uint32 step = 1;</code>
         */
        public int getStep() {
            return step_;
        }

        public static final int DISTANCE_FIELD_NUMBER = 2;
        private int distance_;

        /**
         * <code>optional uint32 distance = 2;</code>
         * <p/>
         * <pre>
         * meter
         * </pre>
         */
        public boolean hasDistance() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }

        /**
         * <code>optional uint32 distance = 2;</code>
         * <p/>
         * <pre>
         * meter
         * </pre>
         */
        public int getDistance() {
            return distance_;
        }

        public static final int CALORIE_FIELD_NUMBER = 3;
        private int calorie_;

        /**
         * <code>optional uint32 calorie = 3;</code>
         * <p/>
         * <pre>
         * calorie KJ
         * </pre>
         */
        public boolean hasCalorie() {
            return ((bitField0_ & 0x00000004) == 0x00000004);
        }

        /**
         * <code>optional uint32 calorie = 3;</code>
         * <p/>
         * <pre>
         * calorie KJ
         * </pre>
         */
        public int getCalorie() {
            return calorie_;
        }

        private void initFields() {
            step_ = 0;
            distance_ = 0;
            calorie_ = 0;
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
                output.writeUInt32(1, step_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                output.writeUInt32(2, distance_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                output.writeUInt32(3, calorie_);
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
                        .computeUInt32Size(1, step_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeUInt32Size(2, distance_);
            }
            if (((bitField0_ & 0x00000004) == 0x00000004)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeUInt32Size(3, calorie_);
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

        public static bt_summary parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_summary parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_summary parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_summary parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_summary parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_summary parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static bt_summary parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }

        public static bt_summary parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static bt_summary parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_summary parseFrom(
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

        public static Builder newBuilder(bt_summary prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        /**
         * Protobuf type {@code bt_summary}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageLite.Builder<
                        bt_summary, Builder>
                implements
                // @@protoc_insertion_point(builder_implements:bt_summary)
                bt_summaryOrBuilder {
            // Construct using BtSummary.bt_summary.newBuilder()
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
                step_ = 0;
                bitField0_ = (bitField0_ & ~0x00000001);
                distance_ = 0;
                bitField0_ = (bitField0_ & ~0x00000002);
                calorie_ = 0;
                bitField0_ = (bitField0_ & ~0x00000004);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public bt_summary getDefaultInstanceForType() {
                return bt_summary.getDefaultInstance();
            }

            public bt_summary build() {
                bt_summary result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public bt_summary buildPartial() {
                bt_summary result = new bt_summary(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.step_ = step_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.distance_ = distance_;
                if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
                    to_bitField0_ |= 0x00000004;
                }
                result.calorie_ = calorie_;
                result.bitField0_ = to_bitField0_;
                return result;
            }

            public Builder mergeFrom(bt_summary other) {
                if (other == bt_summary.getDefaultInstance()) return this;
                if (other.hasStep()) {
                    setStep(other.getStep());
                }
                if (other.hasDistance()) {
                    setDistance(other.getDistance());
                }
                if (other.hasCalorie()) {
                    setCalorie(other.getCalorie());
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
                bt_summary parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (bt_summary) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private int step_;

            /**
             * <code>optional uint32 step = 1;</code>
             */
            public boolean hasStep() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>optional uint32 step = 1;</code>
             */
            public int getStep() {
                return step_;
            }

            /**
             * <code>optional uint32 step = 1;</code>
             */
            public Builder setStep(int value) {
                bitField0_ |= 0x00000001;
                step_ = value;

                return this;
            }

            /**
             * <code>optional uint32 step = 1;</code>
             */
            public Builder clearStep() {
                bitField0_ = (bitField0_ & ~0x00000001);
                step_ = 0;

                return this;
            }

            private int distance_;

            /**
             * <code>optional uint32 distance = 2;</code>
             * <p/>
             * <pre>
             * meter
             * </pre>
             */
            public boolean hasDistance() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }

            /**
             * <code>optional uint32 distance = 2;</code>
             * <p/>
             * <pre>
             * meter
             * </pre>
             */
            public int getDistance() {
                return distance_;
            }

            /**
             * <code>optional uint32 distance = 2;</code>
             * <p/>
             * <pre>
             * meter
             * </pre>
             */
            public Builder setDistance(int value) {
                bitField0_ |= 0x00000002;
                distance_ = value;

                return this;
            }

            /**
             * <code>optional uint32 distance = 2;</code>
             * <p/>
             * <pre>
             * meter
             * </pre>
             */
            public Builder clearDistance() {
                bitField0_ = (bitField0_ & ~0x00000002);
                distance_ = 0;

                return this;
            }

            private int calorie_;

            /**
             * <code>optional uint32 calorie = 3;</code>
             * <p/>
             * <pre>
             * calorie KJ
             * </pre>
             */
            public boolean hasCalorie() {
                return ((bitField0_ & 0x00000004) == 0x00000004);
            }

            /**
             * <code>optional uint32 calorie = 3;</code>
             * <p/>
             * <pre>
             * calorie KJ
             * </pre>
             */
            public int getCalorie() {
                return calorie_;
            }

            /**
             * <code>optional uint32 calorie = 3;</code>
             * <p/>
             * <pre>
             * calorie KJ
             * </pre>
             */
            public Builder setCalorie(int value) {
                bitField0_ |= 0x00000004;
                calorie_ = value;

                return this;
            }

            /**
             * <code>optional uint32 calorie = 3;</code>
             * <p/>
             * <pre>
             * calorie KJ
             * </pre>
             */
            public Builder clearCalorie() {
                bitField0_ = (bitField0_ & ~0x00000004);
                calorie_ = 0;

                return this;
            }

            // @@protoc_insertion_point(builder_scope:bt_summary)
        }

        static {
            defaultInstance = new bt_summary(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:bt_summary)
    }


    static {
    }

    // @@protoc_insertion_point(outer_class_scope)
}