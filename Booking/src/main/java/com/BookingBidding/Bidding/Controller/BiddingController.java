package com.BookingBidding.Bidding.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BookingBidding.Bidding.Exception.EntityNotFoundException;
import com.BookingBidding.Bidding.Model.BidDeleteResponse;
import com.BookingBidding.Bidding.Model.BidPostRequest;
import com.BookingBidding.Bidding.Model.BidPostResponse;
import com.BookingBidding.Bidding.Model.BidPutRequest;
import com.BookingBidding.Bidding.Model.BidPutResponse;
import com.BookingBidding.Bidding.Service.BiddingService;
//import com.BookingBidding.Bidding.Util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import sharedEntity.BiddingData;

@RestController
@Slf4j
public class BiddingController {

//	@Autowired
//	JwtUtil jwtUtil;

	@Autowired
	private BiddingService biddingService;

	@GetMapping("/bid")
	public ResponseEntity<List<BiddingData>> getBid(
			@RequestHeader(value="Authorization",defaultValue="") String token,
			@RequestParam(value = "pageNo", required = false) Integer pageNo,
			@RequestParam(value = "loadId", required = false) String loadId,
			@RequestParam(value = "transporterId", required = false) String transporterId)
					throws EntityNotFoundException {
		log.info("Get with Params Controller Started");

//		jwtUtil.validateToken(token);
		//		System.out.println("token validation successful");
		return new ResponseEntity<>(biddingService.getBid(pageNo, loadId, transporterId,token), HttpStatus.OK);
	}

	@GetMapping("/bid/{Id}")
	public ResponseEntity<BiddingData> getBidById(
			@RequestHeader(value="Authorization",defaultValue="") String token,
			@PathVariable String Id) throws EntityNotFoundException {
		log.info("Get Controller Started");
//		jwtUtil.validateToken(token);
		return new ResponseEntity<>(biddingService.getBidById(Id,token), HttpStatus.OK);
	}

	@PostMapping("/bid")
	public ResponseEntity<BidPostResponse> addBid(
			@RequestHeader(value="Authorization",defaultValue="") String token,
			@RequestBody BidPostRequest bidPostRequest) {
		log.info("Post Controller Started");
//		jwtUtil.validateToken(token);
		return new ResponseEntity<>(biddingService.addBid(bidPostRequest,token), HttpStatus.CREATED);
	}

	@PutMapping("/bid/{id}")
	public ResponseEntity<BidPutResponse> updateBid(
			@RequestHeader(value="Authorization",defaultValue="") String token,
			@PathVariable String id,
			@RequestBody BidPutRequest bidPutRequest)
					throws EntityNotFoundException {
		log.info("Put Controller Started");
//		jwtUtil.validateToken(token);
		return new ResponseEntity<>(biddingService.updateBid(id, bidPutRequest,token), HttpStatus.OK);
	}

	@DeleteMapping("/bid/{Id}")
	public ResponseEntity<BidDeleteResponse> deleteBid(
			@RequestHeader(value="Authorization",defaultValue="") String token,
			@PathVariable String Id) throws EntityNotFoundException {
		log.info("Delete Controller Started");
//		jwtUtil.validateToken(token);
		return new ResponseEntity<>(biddingService.deleteBid(Id,token), HttpStatus.OK);
	}

}
