package com.example.wandermate;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WanderMateDao {
    @Insert
    void insert_route(Route route);

    @Insert
    void insert_stop(Stop stop);

    @Insert
    void insert_bus(Bus bus);

    @Insert
    void insert_route_details(RouteDetails routeDetails);

    @Insert
    void insert_service(Service service);

    @Query("SELECT B.bus_name, "+
           "CASE WHEN (SC.start_time_mins + RS.duration_mins >= 60) THEN (SC.start_time_hrs + RS.duration_hrs + 1) % 24 ELSE (SC.start_time_hrs + RS.duration_hrs) % 24 END AS departure_time_hrs ," +
           "(SC.start_time_mins + RS.duration_mins) % 60 AS departure_time_mins, " +
           "CASE WHEN (SC.start_time_mins + RE.duration_mins >= 60) THEN (SC.start_time_hrs + RE.duration_hrs + 1) % 24 ELSE (SC.start_time_hrs + RE.duration_hrs) % 24 END AS arrival_time_hrs, " +
           "(SC.start_time_mins + RE.duration_mins) % 60 AS arrival_time_mins " +
           "FROM ROUTE R JOIN ROUTEDETAILS RS ON (RS.route_id = R.route_id) JOIN STOP SS ON (SS.stop_id = RS.stop_id) JOIN ROUTEDETAILS RE ON (RE.route_id = R.route_id) " +
           "JOIN STOP SE ON (SE.stop_id = RE.stop_id) JOIN SERVICE SC ON (SC.route_id = R.route_id) JOIN BUS B ON (B.bus_id = SC.bus_id) WHERE SS.stop_name = :start AND " +
           "SE.stop_name = :stop AND RS.stop_number < RE.stop_number ORDER BY departure_time_hrs, departure_time_mins")
    LiveData<List<OutputObject>> getAllServices(String start, String stop);

    @Query("SELECT stop_name FROM STOP")
    LiveData<List<String>> getAllStops();

    @Query("SELECT * FROM STOP")
    LiveData<List<Stop>> getAllCoordinates();

    @Query("SELECT R.route_id, B.bus_id, B.bus_name, SE.stop_name, "+
           "CASE WHEN (SC.start_time_mins + RS.duration_mins >= 60) THEN (SC.start_time_hrs + RS.duration_hrs + 1) % 24 ELSE (SC.start_time_hrs + RS.duration_hrs) % 24 END AS departure_time_hrs, "+
           "(SC.start_time_mins + RS.duration_mins) % 60 AS departure_time_mins "+
           "FROM ROUTE R JOIN ROUTEDETAILS RS ON (RS.route_id = R.route_id) JOIN STOP SS ON (SS.stop_id = RS.stop_id) JOIN ROUTEDETAILS RE ON (RE.route_id = R.route_id)"+
           "JOIN STOP SE ON (SE.stop_id = RE.stop_id) JOIN SERVICE SC ON (SC.route_id = R.route_id) JOIN BUS B ON (B.bus_id = SC.bus_id) WHERE SS.stop_name = :start "+
           "AND R.stops_number = RE.stop_number AND SS.stop_name != SE.stop_name AND ((departure_time_hrs = :time_hr AND departure_time_mins >= :time_min) OR (departure_time_hrs > :time_hr)) ORDER BY departure_time_hrs, departure_time_mins")
    LiveData<List<StopObject>> getStopServices(String start, int time_hr, int time_min);

    @Query("SELECT * FROM STOP WHERE stop_name = :name")
    LiveData<List<Stop>> getCoordinates(String name);

    @Query("SELECT SS.stop_name, "+
           "CASE WHEN (SC.start_time_mins + RS.duration_mins >= 60) THEN (SC.start_time_hrs + RS.duration_hrs + 1) % 24 ELSE (SC.start_time_hrs + RS.duration_hrs) % 24 END AS departure_time_hrs ," +
           "(SC.start_time_mins + RS.duration_mins) % 60 AS departure_time_mins " +
           "FROM ROUTE R JOIN ROUTEDETAILS RS ON (RS.route_id = R.route_id) JOIN STOP SS ON (SS.stop_id = RS.stop_id) " +
           "JOIN SERVICE SC ON (SC.route_id = R.route_id) JOIN BUS B ON (B.bus_id = SC.bus_id) WHERE R.route_id = :rid AND " +
           "B.bus_id = :bid AND RS.stop_number >= (SELECT RSF.stop_number FROM ROUTE RF JOIN ROUTEDETAILS RSF ON (RSF.route_id = RF.route_id) JOIN STOP SSF ON (SSF.stop_id = RSF.stop_id) " +
           "JOIN SERVICE SCF ON (SCF.route_id = RF.route_id) JOIN BUS BF ON (BF.bus_id = SCF.bus_id) WHERE RF.route_id = :rid AND BF.bus_id = :bid AND SSF.stop_name = :name) ORDER BY RS.stop_number")
    LiveData<List<ServiceDetailsObject>> getServiceDetails(int rid, int bid, String name);
}
