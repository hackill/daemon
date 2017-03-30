package com.ble.lib.x2.proto;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bt_vibrate.proto

public final class BtVibrate {
    private BtVibrate() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public interface bt_vibrateOrBuilder extends
            // @@protoc_insertion_point(interface_extends:bt_vibrate)
            com.google.protobuf.MessageLiteOrBuilder {

        /**
         * <code>optional bool on = 1;</code>
         * <p/>
         * <pre>
         * true on, false off
         * </pre>
         */
        boolean hasOn();

        /**
         * <code>optional bool on = 1;</code>
         * <p/>
         * <pre>
         * true on, false off
         * </pre>
         */
        boolean getOn();
    }

    /**
     * Protobuf type {@code bt_vibrate}
     */
    public static final class bt_vibrate extends
            com.google.protobuf.GeneratedMessageLite implements
            // @@protoc_insertion_point(message_implements:bt_vibrate)
            bt_vibrateOrBuilder {
        // Use bt_vibrate.newBuilder() to construct.
        private bt_vibrate(com.google.protobuf.GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private bt_vibrate(boolean noInit) {
            this.unknownFields = com.google.protobuf.ByteString.EMPTY;
        }

        private static final bt_vibrate defaultInstance;

        public static bt_vibrate getDefaultInstance() {
            return defaultInstance;
        }

        public bt_vibrate getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.ByteString unknownFields;

        private bt_vibrate(
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
                            on_ = input.readBool();
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

        public static com.google.protobuf.Parser<bt_vibrate> PARSER =
                new com.google.protobuf.AbstractParser<bt_vibrate>() {
                    public bt_vibrate parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new bt_vibrate(input, extensionRegistry);
                    }
                };

        @Override
        public com.google.protobuf.Parser<bt_vibrate> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        public static final int ON_FIELD_NUMBER = 1;
        private boolean on_;

        /**
         * <code>optional bool on = 1;</code>
         * <p/>
         * <pre>
         * true on, false off
         * </pre>
         */
        public boolean hasOn() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>optional bool on = 1;</code>
         * <p/>
         * <pre>
         * true on, false off
         * </pre>
         */
        public boolean getOn() {
            return on_;
        }

        private void initFields() {
            on_ = false;
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
                output.writeBool(1, on_);
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
                        .computeBoolSize(1, on_);
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

        public static bt_vibrate parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_vibrate parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_vibrate parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_vibrate parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_vibrate parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_vibrate parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static bt_vibrate parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }

        public static bt_vibrate parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static bt_vibrate parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_vibrate parseFrom(
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

        public static Builder newBuilder(bt_vibrate prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        /**
         * Protobuf type {@code bt_vibrate}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageLite.Builder<
                        bt_vibrate, Builder>
                implements
                // @@protoc_insertion_point(builder_implements:bt_vibrate)
                bt_vibrateOrBuilder {
            // Construct using BtVibrate.bt_vibrate.newBuilder()
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
                on_ = false;
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public bt_vibrate getDefaultInstanceForType() {
                return bt_vibrate.getDefaultInstance();
            }

            public bt_vibrate build() {
                bt_vibrate result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public bt_vibrate buildPartial() {
                bt_vibrate result = new bt_vibrate(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.on_ = on_;
                result.bitField0_ = to_bitField0_;
                return result;
            }

            public Builder mergeFrom(bt_vibrate other) {
                if (other == bt_vibrate.getDefaultInstance()) return this;
                if (other.hasOn()) {
                    setOn(other.getOn());
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
                bt_vibrate parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (bt_vibrate) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private boolean on_;

            /**
             * <code>optional bool on = 1;</code>
             * <p/>
             * <pre>
             * true on, false off
             * </pre>
             */
            public boolean hasOn() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>optional bool on = 1;</code>
             * <p/>
             * <pre>
             * true on, false off
             * </pre>
             */
            public boolean getOn() {
                return on_;
            }

            /**
             * <code>optional bool on = 1;</code>
             * <p/>
             * <pre>
             * true on, false off
             * </pre>
             */
            public Builder setOn(boolean value) {
                bitField0_ |= 0x00000001;
                on_ = value;

                return this;
            }

            /**
             * <code>optional bool on = 1;</code>
             * <p/>
             * <pre>
             * true on, false off
             * </pre>
             */
            public Builder clearOn() {
                bitField0_ = (bitField0_ & ~0x00000001);
                on_ = false;

                return this;
            }

            // @@protoc_insertion_point(builder_scope:bt_vibrate)
        }

        static {
            defaultInstance = new bt_vibrate(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:bt_vibrate)
    }


    static {
    }

    // @@protoc_insertion_point(outer_class_scope)
}
