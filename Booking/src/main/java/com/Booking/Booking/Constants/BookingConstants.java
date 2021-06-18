package com.Booking.Booking.Constants;

public class BookingConstants {

	public static String pLoadIdIsNull = "Failed: LoadId cannot be Null";
	public static String pTransporterIdIsNull = "Failed: TransporterId cannot be Null";
	public static String pPostLoadIdIsNull = "Failed: postLoadId cannot be Null";
	public static String pTruckIdIsNull = "Failed: TruckId cannot be Null";
	public static String pDataExists = "Failed: Booking Data Already Exists with given LoadId and TransporterId";
	public static String pUnitIsNull = "Failed: Provide unit when rate is provided";
	public static String pUnknownUnit = "Failed: Cannot provide unknown unit";
	public static String pPostUnitRateIsNull = "Failed: Cannot Provide Unit when Rate is empty";
	public static String uDataNotFound = "Failed: Booking Data doesn't exists for given booking Id";
	public static String uTruckIdIsNull = "Failed: TruckId cannot be Empty after updating";
	public static String uCancelAndCompleteTrue = "Failed: Both Cancel and Completed Cannot be true Simultaneously";
	public static String uUnitIsNull = "Failed: Provide unit when rate is provided";
	public static String uUnknownUnit = "Failed: Cannot provide unknown unit";
	public static String uUpdateUnitRateIsNull = "Failed: Cannot Update Unit without providing rate";
	public static String uCanelIsTrueWhenCompleteIsTrue = "Failed: Cannot assign cancel true when Completed is already true";
	
	public static String success = "Success";
	
}
