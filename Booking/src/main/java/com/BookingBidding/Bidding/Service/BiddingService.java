package com.BookingBidding.Bidding.Service;

import java.util.List;


import com.BookingBidding.Bidding.Model.BidDeleteResponse;
import com.BookingBidding.Bidding.Model.BidPostRequest;
import com.BookingBidding.Bidding.Model.BidPostResponse;
import com.BookingBidding.Bidding.Model.BidPutRequest;
import com.BookingBidding.Bidding.Model.BidPutResponse;
import sharedEntity.BiddingData;

public interface BiddingService {

	public BidPostResponse addBid(BidPostRequest bidPostRequest,String token);

	public List<BiddingData> getBid(Integer pageNo, String loadId, String transporterId,String token);

	public BidDeleteResponse deleteBid(String id,String token);

	public BiddingData getBidById(String id,String token);

	public BidPutResponse updateBid(String id, BidPutRequest bidPutRequest,String token);

}
