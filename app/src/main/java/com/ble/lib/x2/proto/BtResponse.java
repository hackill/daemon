package com.ble.lib.x2.proto;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bt_response.proto

public final class BtResponse {
    private BtResponse() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public interface bt_responseOrBuilder extends
            // @@protoc_insertion_point(interface_extends:bt_response)
            com.google.protobuf.MessageLiteOrBuilder {

        /**
         * <code>optional uint32 command = 1;</code>
         */
        boolean hasCommand();

        /**
         * <code>optional uint32 command = 1;</code>
         */
        int getCommand();

        /**
         * <code>optional uint32 response_value = 2;</code>
         */
        boolean hasResponseValue();

        /**
         * <code>optional uint32 response_value = 2;</code>
         */
        int getResponseValue();
    }

    /**
     * Protobuf type {@code bt_response}
     */
    public static final class bt_response extends
            com.google.protobuf.GeneratedMessageLite implements
            // @@protoc_insertion_point(message_implements:bt_response)
            bt_responseOrBuilder {
        // Use bt_response.newBuilder() to construct.
        private bt_response(com.google.protobuf.GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private bt_response(boolean noInit) {
            this.unknownFields = com.google.protobuf.ByteString.EMPTY;
        }

        private static final bt_response defaultInstance;

        public static bt_response getDefaultInstance() {
            return defaultInstance;
        }

        public bt_response getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.ByteString unknownFields;

        private bt_response(
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
                            command_ = input.readUInt32();
                            break;
                        }
                        case 16: {
                            bitField0_ |= 0x00000002;
                            responseValue_ = input.readUInt32();
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

        public static com.google.protobuf.Parser<bt_response> PARSER =
                new com.google.protobuf.AbstractParser<bt_response>() {
                    public bt_response parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new bt_response(input, extensionRegistry);
                    }
                };

        @Override
        public com.google.protobuf.Parser<bt_response> getParserForType() {
            return PARSER;
        }

        private int bitField0_;
        public static final int COMMAND_FIELD_NUMBER = 1;
        private int command_;

        /**
         * <code>optional uint32 command = 1;</code>
         */
        public boolean hasCommand() {
            return ((bitField0_ & 0x00000001) == 0x00000001);
        }

        /**
         * <code>optional uint32 command = 1;</code>
         */
        public int getCommand() {
            return command_;
        }

        public static final int RESPONSE_VALUE_FIELD_NUMBER = 2;
        private int responseValue_;

        /**
         * <code>optional uint32 response_value = 2;</code>
         */
        public boolean hasResponseValue() {
            return ((bitField0_ & 0x00000002) == 0x00000002);
        }

        /**
         * <code>optional uint32 response_value = 2;</code>
         */
        public int getResponseValue() {
            return responseValue_;
        }

        private void initFields() {
            command_ = 0;
            responseValue_ = 0;
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
                output.writeUInt32(1, command_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                output.writeUInt32(2, responseValue_);
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
                        .computeUInt32Size(1, command_);
            }
            if (((bitField0_ & 0x00000002) == 0x00000002)) {
                size += com.google.protobuf.CodedOutputStream
                        .computeUInt32Size(2, responseValue_);
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

        public static bt_response parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_response parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_response parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_response parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_response parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_response parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static bt_response parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }

        public static bt_response parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static bt_response parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_response parseFrom(
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

        public static Builder newBuilder(bt_response prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        /**
         * Protobuf type {@code bt_response}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageLite.Builder<
                        bt_response, Builder>
                implements
                // @@protoc_insertion_point(builder_implements:bt_response)
                bt_responseOrBuilder {
            // Construct using BtResponse.bt_response.newBuilder()
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
                command_ = 0;
                bitField0_ = (bitField0_ & ~0x00000001);
                responseValue_ = 0;
                bitField0_ = (bitField0_ & ~0x00000002);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public bt_response getDefaultInstanceForType() {
                return bt_response.getDefaultInstance();
            }

            public bt_response build() {
                bt_response result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public bt_response buildPartial() {
                bt_response result = new bt_response(this);
                int from_bitField0_ = bitField0_;
                int to_bitField0_ = 0;
                if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
                    to_bitField0_ |= 0x00000001;
                }
                result.command_ = command_;
                if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
                    to_bitField0_ |= 0x00000002;
                }
                result.responseValue_ = responseValue_;
                result.bitField0_ = to_bitField0_;
                return result;
            }

            public Builder mergeFrom(bt_response other) {
                if (other == bt_response.getDefaultInstance()) return this;
                if (other.hasCommand()) {
                    setCommand(other.getCommand());
                }
                if (other.hasResponseValue()) {
                    setResponseValue(other.getResponseValue());
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
                bt_response parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (bt_response) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private int command_;

            /**
             * <code>optional uint32 command = 1;</code>
             */
            public boolean hasCommand() {
                return ((bitField0_ & 0x00000001) == 0x00000001);
            }

            /**
             * <code>optional uint32 command = 1;</code>
             */
            public int getCommand() {
                return command_;
            }

            /**
             * <code>optional uint32 command = 1;</code>
             */
            public Builder setCommand(int value) {
                bitField0_ |= 0x00000001;
                command_ = value;

                return this;
            }

            /**
             * <code>optional uint32 command = 1;</code>
             */
            public Builder clearCommand() {
                bitField0_ = (bitField0_ & ~0x00000001);
                command_ = 0;

                return this;
            }

            private int responseValue_;

            /**
             * <code>optional uint32 response_value = 2;</code>
             */
            public boolean hasResponseValue() {
                return ((bitField0_ & 0x00000002) == 0x00000002);
            }

            /**
             * <code>optional uint32 response_value = 2;</code>
             */
            public int getResponseValue() {
                return responseValue_;
            }

            /**
             * <code>optional uint32 response_value = 2;</code>
             */
            public Builder setResponseValue(int value) {
                bitField0_ |= 0x00000002;
                responseValue_ = value;

                return this;
            }

            /**
             * <code>optional uint32 response_value = 2;</code>
             */
            public Builder clearResponseValue() {
                bitField0_ = (bitField0_ & ~0x00000002);
                responseValue_ = 0;

                return this;
            }

            // @@protoc_insertion_point(builder_scope:bt_response)
        }

        static {
            defaultInstance = new bt_response(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:bt_response)
    }


    static {
    }

    // @@protoc_insertion_point(outer_class_scope)
}