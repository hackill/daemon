package com.ble.lib.x2.proto;// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: bt_card_order.proto

public final class BtCardOrder {
    private BtCardOrder() {
    }

    public static void registerAllExtensions(
            com.google.protobuf.ExtensionRegistryLite registry) {
    }

    public interface bt_card_orderOrBuilder extends
            // @@protoc_insertion_point(interface_extends:bt_card_order)
            com.google.protobuf.MessageLiteOrBuilder {

        /**
         * <code>repeated uint32 cardid = 1;</code>
         * <p/>
         * <pre>
         * </pre>
         */
        java.util.List<Integer> getCardidList();

        /**
         * <code>repeated uint32 cardid = 1;</code>
         * <p/>
         * <pre>
         * </pre>
         */
        int getCardidCount();

        /**
         * <code>repeated uint32 cardid = 1;</code>
         * <p/>
         * <pre>
         * </pre>
         */
        int getCardid(int index);
    }

    /**
     * Protobuf type {@code bt_card_order}
     */
    public static final class bt_card_order extends
            com.google.protobuf.GeneratedMessageLite implements
            // @@protoc_insertion_point(message_implements:bt_card_order)
            bt_card_orderOrBuilder {
        // Use bt_card_order.newBuilder() to construct.
        private bt_card_order(com.google.protobuf.GeneratedMessageLite.Builder builder) {
            super(builder);
            this.unknownFields = builder.getUnknownFields();
        }

        private bt_card_order(boolean noInit) {
            this.unknownFields = com.google.protobuf.ByteString.EMPTY;
        }

        private static final bt_card_order defaultInstance;

        public static bt_card_order getDefaultInstance() {
            return defaultInstance;
        }

        public bt_card_order getDefaultInstanceForType() {
            return defaultInstance;
        }

        private final com.google.protobuf.ByteString unknownFields;

        private bt_card_order(
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
                            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                                cardid_ = new java.util.ArrayList<Integer>();
                                mutable_bitField0_ |= 0x00000001;
                            }
                            cardid_.add(input.readUInt32());
                            break;
                        }
                        case 10: {
                            int length = input.readRawVarint32();
                            int limit = input.pushLimit(length);
                            if (!((mutable_bitField0_ & 0x00000001) == 0x00000001) && input.getBytesUntilLimit() > 0) {
                                cardid_ = new java.util.ArrayList<Integer>();
                                mutable_bitField0_ |= 0x00000001;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                cardid_.add(input.readUInt32());
                            }
                            input.popLimit(limit);
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
                if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                    cardid_ = java.util.Collections.unmodifiableList(cardid_);
                }
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

        public static com.google.protobuf.Parser<bt_card_order> PARSER =
                new com.google.protobuf.AbstractParser<bt_card_order>() {
                    public bt_card_order parsePartialFrom(
                            com.google.protobuf.CodedInputStream input,
                            com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                            throws com.google.protobuf.InvalidProtocolBufferException {
                        return new bt_card_order(input, extensionRegistry);
                    }
                };

        @Override
        public com.google.protobuf.Parser<bt_card_order> getParserForType() {
            return PARSER;
        }

        public static final int CARDID_FIELD_NUMBER = 1;
        private java.util.List<Integer> cardid_;

        /**
         * <code>repeated uint32 cardid = 1;</code>
         * <p/>
         * <pre>
         * </pre>
         */
        public java.util.List<Integer>
        getCardidList() {
            return cardid_;
        }

        /**
         * <code>repeated uint32 cardid = 1;</code>
         * <p/>
         * <pre>
         * </pre>
         */
        public int getCardidCount() {
            return cardid_.size();
        }

        /**
         * <code>repeated uint32 cardid = 1;</code>
         * <p/>
         * <pre>
         * </pre>
         */
        public int getCardid(int index) {
            return cardid_.get(index);
        }

        private void initFields() {
            cardid_ = java.util.Collections.emptyList();
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
            for (int i = 0; i < cardid_.size(); i++) {
                output.writeUInt32(1, cardid_.get(i));
            }
            output.writeRawBytes(unknownFields);
        }

        private int memoizedSerializedSize = -1;

        public int getSerializedSize() {
            int size = memoizedSerializedSize;
            if (size != -1) return size;

            size = 0;
            {
                int dataSize = 0;
                for (int i = 0; i < cardid_.size(); i++) {
                    dataSize += com.google.protobuf.CodedOutputStream
                            .computeUInt32SizeNoTag(cardid_.get(i));
                }
                size += dataSize;
                size += getCardidList().size();
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

        public static bt_card_order parseFrom(
                com.google.protobuf.ByteString data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_card_order parseFrom(
                com.google.protobuf.ByteString data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_card_order parseFrom(byte[] data)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data);
        }

        public static bt_card_order parseFrom(
                byte[] data,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws com.google.protobuf.InvalidProtocolBufferException {
            return PARSER.parseFrom(data, extensionRegistry);
        }

        public static bt_card_order parseFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_card_order parseFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseFrom(input, extensionRegistry);
        }

        public static bt_card_order parseDelimitedFrom(java.io.InputStream input)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input);
        }

        public static bt_card_order parseDelimitedFrom(
                java.io.InputStream input,
                com.google.protobuf.ExtensionRegistryLite extensionRegistry)
                throws java.io.IOException {
            return PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static bt_card_order parseFrom(
                com.google.protobuf.CodedInputStream input)
                throws java.io.IOException {
            return PARSER.parseFrom(input);
        }

        public static bt_card_order parseFrom(
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

        public static Builder newBuilder(bt_card_order prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        /**
         * Protobuf type {@code bt_card_order}
         */
        public static final class Builder extends
                com.google.protobuf.GeneratedMessageLite.Builder<
                        bt_card_order, Builder>
                implements
                // @@protoc_insertion_point(builder_implements:bt_card_order)
                bt_card_orderOrBuilder {
            // Construct using BtCardOrder.bt_card_order.newBuilder()
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
                cardid_ = java.util.Collections.emptyList();
                bitField0_ = (bitField0_ & ~0x00000001);
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public bt_card_order getDefaultInstanceForType() {
                return bt_card_order.getDefaultInstance();
            }

            public bt_card_order build() {
                bt_card_order result = buildPartial();
                if (!result.isInitialized()) {
                    throw newUninitializedMessageException(result);
                }
                return result;
            }

            public bt_card_order buildPartial() {
                bt_card_order result = new bt_card_order(this);
                int from_bitField0_ = bitField0_;
                if (((bitField0_ & 0x00000001) == 0x00000001)) {
                    cardid_ = java.util.Collections.unmodifiableList(cardid_);
                    bitField0_ = (bitField0_ & ~0x00000001);
                }
                result.cardid_ = cardid_;
                return result;
            }

            public Builder mergeFrom(bt_card_order other) {
                if (other == bt_card_order.getDefaultInstance()) return this;
                if (!other.cardid_.isEmpty()) {
                    if (cardid_.isEmpty()) {
                        cardid_ = other.cardid_;
                        bitField0_ = (bitField0_ & ~0x00000001);
                    } else {
                        ensureCardidIsMutable();
                        cardid_.addAll(other.cardid_);
                    }

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
                bt_card_order parsedMessage = null;
                try {
                    parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
                } catch (com.google.protobuf.InvalidProtocolBufferException e) {
                    parsedMessage = (bt_card_order) e.getUnfinishedMessage();
                    throw e;
                } finally {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
                return this;
            }

            private int bitField0_;

            private java.util.List<Integer> cardid_ = java.util.Collections.emptyList();

            private void ensureCardidIsMutable() {
                if (!((bitField0_ & 0x00000001) == 0x00000001)) {
                    cardid_ = new java.util.ArrayList<Integer>(cardid_);
                    bitField0_ |= 0x00000001;
                }
            }

            /**
             * <code>repeated uint32 cardid = 1;</code>
             * <p/>
             * <pre>
             * </pre>
             */
            public java.util.List<Integer>
            getCardidList() {
                return java.util.Collections.unmodifiableList(cardid_);
            }

            /**
             * <code>repeated uint32 cardid = 1;</code>
             * <p/>
             * <pre>
             * </pre>
             */
            public int getCardidCount() {
                return cardid_.size();
            }

            /**
             * <code>repeated uint32 cardid = 1;</code>
             * <p/>
             * <pre>
             * </pre>
             */
            public int getCardid(int index) {
                return cardid_.get(index);
            }

            /**
             * <code>repeated uint32 cardid = 1;</code>
             * <p/>
             * <pre>
             * </pre>
             */
            public Builder setCardid(
                    int index, int value) {
                ensureCardidIsMutable();
                cardid_.set(index, value);

                return this;
            }

            /**
             * <code>repeated uint32 cardid = 1;</code>
             * <p/>
             * <pre>
             * </pre>
             */
            public Builder addCardid(int value) {
                ensureCardidIsMutable();
                cardid_.add(value);

                return this;
            }

            /**
             * <code>repeated uint32 cardid = 1;</code>
             * <p/>
             * <pre>
             * </pre>
             */
            public Builder addAllCardid(
                    Iterable<? extends Integer> values) {
                ensureCardidIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(
                        values, cardid_);

                return this;
            }

            /**
             * <code>repeated uint32 cardid = 1;</code>
             * <p/>
             * <pre>
             * </pre>
             */
            public Builder clearCardid() {
                cardid_ = java.util.Collections.emptyList();
                bitField0_ = (bitField0_ & ~0x00000001);

                return this;
            }

            // @@protoc_insertion_point(builder_scope:bt_card_order)
        }

        static {
            defaultInstance = new bt_card_order(true);
            defaultInstance.initFields();
        }

        // @@protoc_insertion_point(class_scope:bt_card_order)
    }


    static {
    }

    // @@protoc_insertion_point(outer_class_scope)
}