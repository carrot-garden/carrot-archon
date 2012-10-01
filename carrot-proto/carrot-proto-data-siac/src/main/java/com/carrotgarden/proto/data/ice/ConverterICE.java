package com.carrotgarden.proto.data.ice;

import com.carrotgarden.proto.data.MarketMessage.Builder;
import com.carrotgarden.proto.data.siac.Converter;
import com.carrotgarden.proto.data.siac.NYL.SnapshotFlag;

public class ConverterICE implements Converter {

	public boolean isKnown(final int type) {
		// TODO Auto-generated method stub
		return false;
	}

	public void applyType(final int type, final Builder message,
			final com.carrotgarden.proto.data.MarketEntry.Builder entry) {
		// TODO Auto-generated method stub

	}

	public void applyPrice(final int price, final Builder message,
			final com.carrotgarden.proto.data.MarketEntry.Builder entry) {
		// TODO Auto-generated method stub

	}

	public void applySize(final int size, final Builder message,
			final com.carrotgarden.proto.data.MarketEntry.Builder entry) {
		// TODO Auto-generated method stub

	}

	public com.carrotgarden.proto.data.MarketMessage.Type dataType(
			final SnapshotFlag snapshotFlagEnum) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long targetId(final byte sourceId, final String securityId) {
		// TODO Auto-generated method stub
		return null;
	}

	public long timeStamp(final long timeStamp) {
		// TODO Auto-generated method stub
		return 0;
	}

}
