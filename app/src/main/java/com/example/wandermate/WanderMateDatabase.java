package com.example.wandermate;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Route.class, Stop.class, Bus.class, RouteDetails.class, Service.class}, version = 1)
public abstract class WanderMateDatabase extends RoomDatabase {
    private static final String DB_NAME = "wander_mate";
    private static WanderMateDatabase instance;

    public static synchronized WanderMateDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), WanderMateDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    public abstract WanderMateDao wanderMateDao();

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new WanderMateDatabase.PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {
        private WanderMateDao wanderMateDao;

        public PopulateDBAsyncTask(WanderMateDatabase db) {
            wanderMateDao = db.wanderMateDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO: Add data to the database
            wanderMateDao.insert_route(new Route(1, "PDM-KTM", 5));
            wanderMateDao.insert_route(new Route(2, "TVM-PNM", 23));
            wanderMateDao.insert_route(new Route(3, "TVM-TSY", 26));
            wanderMateDao.insert_route(new Route(4, "TVM-MLP", 24));
            wanderMateDao.insert_route(new Route(5, "TVM-MLP", 23));

            wanderMateDao.insert_stop(new Stop(1, "Pandalam KSRTC Bus Station", 9.225407, 76.674565));
            wanderMateDao.insert_stop(new Stop(2, "Chengannur KSRTC Bus Station", 9.318343, 76.612990));
            wanderMateDao.insert_stop(new Stop(3, "Thiruvalla KSRTC Bus Station", 9.386564, 76.574473));
            wanderMateDao.insert_stop(new Stop(4, "Changanassery KSRTC Bus Station", 9.445678, 76.540909));
            wanderMateDao.insert_stop(new Stop(5, "Kottayam KSRTC Bus Station", 9.585719, 76.523438));
            wanderMateDao.insert_stop(new Stop(6, "Thiruvananthapuram KSRTC Bus Station", 8.488315, 76.952160));
            wanderMateDao.insert_stop(new Stop(7, "Venjaramoodu KSRTC Bus Station", 8.677908, 76.910470));
            wanderMateDao.insert_stop(new Stop(8, "Kilimanoor KSRTC Bus Station", 8.774899, 76.880560));
            wanderMateDao.insert_stop(new Stop(9, "Chadayamangalam KSRTC Bus Station", 8.872400, 76.869109));
            wanderMateDao.insert_stop(new Stop(10, "Ayoor KSRTC Bus Station", 8.898567, 76.859699));
            wanderMateDao.insert_stop(new Stop(11, "Kottarakkara KSRTC Bus Station", 9.006061, 76.781918));
            wanderMateDao.insert_stop(new Stop(12, "Adoor KSRTC Bus Station", 9.152839, 76.733111));
            wanderMateDao.insert_stop(new Stop(13, "Ettumanoor KSRTC Bus Station", 9.667796, 76.555942));
            wanderMateDao.insert_stop(new Stop(14, "Koothattukulam KSRTC Bus Station", 9.863363, 76.592747));
            wanderMateDao.insert_stop(new Stop(15, "Muvattupuzha KSRTC Bus Station", 9.977397, 76.579660));
            wanderMateDao.insert_stop(new Stop(16, "Perumbavoor KSRTC Bus Station", 10.110964, 76.482716));
            wanderMateDao.insert_stop(new Stop(17, "Angamaly KSRTC Bus Station", 10.195806, 76.386338));
            wanderMateDao.insert_stop(new Stop(18, "Chalakudy KSRTC Bus Station", 10.301593, 76.332938));
            wanderMateDao.insert_stop(new Stop(19, "Puthukkad KSRTC Bus Station", 10.425747, 76.268114));
            wanderMateDao.insert_stop(new Stop(20, "Thrissur KSRTC Bus Station", 10.517526, 76.210430));
            wanderMateDao.insert_stop(new Stop(21, "Shornur KSRTC Bus Station", 10.763239, 76.269906));
            wanderMateDao.insert_stop(new Stop(22, "Pattambi KSRTC Bus Station", 10.801734, 76.179911));
            wanderMateDao.insert_stop(new Stop(23, "Perinthalmanna KSRTC Bus Station", 10.975154, 76.229953));
            wanderMateDao.insert_stop(new Stop(24, "Indira Junction Bus Stop", 9.223129, 76.729904));
            wanderMateDao.insert_stop(new Stop(25, "Kaniyapuram KSRTC Bus Station", 8.587734, 76.858672));
            wanderMateDao.insert_stop(new Stop(26, "Attingal KSRTC Bus Station", 8.695289, 76.818726));
            wanderMateDao.insert_stop(new Stop(27, "Chathannoor KSRTC Bus Station", 8.857060, 76.721927));
            wanderMateDao.insert_stop(new Stop(28, "Kottiyam KSRTC Bus Station", 8.865887, 76.671950));
            wanderMateDao.insert_stop(new Stop(29, "Kollam KSRTC Bus Station", 8.891162, 76.585119));
            wanderMateDao.insert_stop(new Stop(30, "Karunagappally KSRTC Bus Station", 9.051852, 76.536277));
            wanderMateDao.insert_stop(new Stop(31, "Oachira KSRTC Bus Station", 9.135195, 76.512670));
            wanderMateDao.insert_stop(new Stop(32, "Kayamakulam KSRTC Bus Station", 9.172635, 76.497968));
            wanderMateDao.insert_stop(new Stop(33, "Haripad KSRTC Bus Station", 9.279357, 76.459197));
            wanderMateDao.insert_stop(new Stop(34, "Ambalappuzha KSRTC Bus Station", 9.383162, 76.355164));
            wanderMateDao.insert_stop(new Stop(35, "Alappuzha KSRTC Bus Station", 9.500050, 76.346651));
            wanderMateDao.insert_stop(new Stop(36, "Cherthala KSRTC Bus Station", 9.686197, 76.343847));
            wanderMateDao.insert_stop(new Stop(37, "Vytilla Hub KSRTC Bus Station", 9.969409, 76.321495));
            wanderMateDao.insert_stop(new Stop(38, "Aluva KSRTC Bus Station", 10.106379, 76.356240));
            wanderMateDao.insert_stop(new Stop(39, "Manjeri KSRTC Bus Station", 11.119417, 76.122752));
            wanderMateDao.insert_stop(new Stop(40, "Areakode KSRTC Bus Station", 11.234881, 76.051239));
            wanderMateDao.insert_stop(new Stop(41, "Mukkam KSRTC Bus Station", 11.320808, 75.997331));
            wanderMateDao.insert_stop(new Stop(42, "Thamarassery KSRTC Bus Station", 11.409126, 75.935574));
            wanderMateDao.insert_stop(new Stop(43, "Kunnamkulam KSRTC Bus Station", 10.649865, 76.067293));
            wanderMateDao.insert_stop(new Stop(44, "Malappuram KSRTC Bus Station", 11.042113, 76.079812));
            wanderMateDao.insert_stop(new Stop(45, "Pandalam Private Bus Stand", 9.224976, 76.677852));
            wanderMateDao.insert_stop(new Stop(46, "Kakka Mukku Bus Stop", 9.222956, 76.733280));
            wanderMateDao.insert_stop(new Stop(47, "Nariyapuram Bus Stop", 9.222965, 76.737220));
            wanderMateDao.insert_stop(new Stop(48, "Angamaly - City Tower Bus Stop", 10.195521, 76.385754));
            wanderMateDao.insert_stop(new Stop(49, "Pathanamthitta New Private Bus Station", 9.266400, 76.790871));
            wanderMateDao.insert_stop(new Stop(50, "Pathanamthitta KSRTC Bus Station", 9.267485, 76.790941));
            wanderMateDao.insert_stop(new Stop(51, "Pathanamthitta Old Bus Stand", 9.265772, 76.786928));
            wanderMateDao.insert_stop(new Stop(52, "Pala KSRTC Bus Station", 9.714653, 76.688593));
            wanderMateDao.insert_stop(new Stop(53, "Pala Private Bus Stand", 9.712389, 76.684733));

            wanderMateDao.insert_route_details(new RouteDetails(1, 1, 1, 0, 0));
            wanderMateDao.insert_route_details(new RouteDetails(1, 2, 2, 0, 20));
            wanderMateDao.insert_route_details(new RouteDetails(1, 3, 3, 0, 35));
            wanderMateDao.insert_route_details(new RouteDetails(1, 4, 4, 0, 45));
            wanderMateDao.insert_route_details(new RouteDetails(1, 5, 5, 1, 5));
            wanderMateDao.insert_route_details(new RouteDetails(2, 6, 1, 0, 0));
            wanderMateDao.insert_route_details(new RouteDetails(2, 7, 2, 0, 40));
            wanderMateDao.insert_route_details(new RouteDetails(2, 8, 3, 1, 0));
            wanderMateDao.insert_route_details(new RouteDetails(2, 9, 4, 1, 15));
            wanderMateDao.insert_route_details(new RouteDetails(2, 10, 5, 1, 20));
            wanderMateDao.insert_route_details(new RouteDetails(2, 11, 6, 1, 45));
            wanderMateDao.insert_route_details(new RouteDetails(2, 12, 7, 2, 15));
            wanderMateDao.insert_route_details(new RouteDetails(2, 1, 8, 2, 30));
            wanderMateDao.insert_route_details(new RouteDetails(2, 2, 9, 2, 50));
            wanderMateDao.insert_route_details(new RouteDetails(2, 3, 10, 3, 5));
            wanderMateDao.insert_route_details(new RouteDetails(2, 4, 11, 3, 15));
            wanderMateDao.insert_route_details(new RouteDetails(2, 5, 12, 3, 35));
            wanderMateDao.insert_route_details(new RouteDetails(2, 13, 13, 4, 25));
            wanderMateDao.insert_route_details(new RouteDetails(2, 14, 14, 5, 10));
            wanderMateDao.insert_route_details(new RouteDetails(2, 15, 15, 5, 30));
            wanderMateDao.insert_route_details(new RouteDetails(2, 16, 16, 6, 0));
            wanderMateDao.insert_route_details(new RouteDetails(2, 17, 17, 6, 20));
            wanderMateDao.insert_route_details(new RouteDetails(2, 18, 18, 6, 40));
            wanderMateDao.insert_route_details(new RouteDetails(2, 19, 19, 7, 0));
            wanderMateDao.insert_route_details(new RouteDetails(2, 20, 20, 7, 20));
            wanderMateDao.insert_route_details(new RouteDetails(2, 21, 21, 8, 20));
            wanderMateDao.insert_route_details(new RouteDetails(2, 22, 22, 8, 35));
            wanderMateDao.insert_route_details(new RouteDetails(2, 23, 23, 9, 10));
            wanderMateDao.insert_route_details(new RouteDetails(3, 6, 1, 0, 0));
            wanderMateDao.insert_route_details(new RouteDetails(3, 25, 2, 0, 25));
            wanderMateDao.insert_route_details(new RouteDetails(3, 26, 3, 0, 45));
            wanderMateDao.insert_route_details(new RouteDetails(3, 27, 4, 1, 15));
            wanderMateDao.insert_route_details(new RouteDetails(3, 28, 5, 1, 20));
            wanderMateDao.insert_route_details(new RouteDetails(3, 29, 6, 1, 40));
            wanderMateDao.insert_route_details(new RouteDetails(3, 30, 7, 2, 10));
            wanderMateDao.insert_route_details(new RouteDetails(3, 31, 8, 2, 25));
            wanderMateDao.insert_route_details(new RouteDetails(3, 32, 9, 2, 30));
            wanderMateDao.insert_route_details(new RouteDetails(3, 33, 10, 2, 50));
            wanderMateDao.insert_route_details(new RouteDetails(3, 34, 11, 3, 10));
            wanderMateDao.insert_route_details(new RouteDetails(3, 35, 12, 3, 35));
            wanderMateDao.insert_route_details(new RouteDetails(3, 36, 13, 4, 35));
            wanderMateDao.insert_route_details(new RouteDetails(3, 37, 14, 5, 30));
            wanderMateDao.insert_route_details(new RouteDetails(3, 38, 15, 6, 0));
            wanderMateDao.insert_route_details(new RouteDetails(3, 17, 16, 6, 20));
            wanderMateDao.insert_route_details(new RouteDetails(3, 18, 17, 6, 40));
            wanderMateDao.insert_route_details(new RouteDetails(3, 19, 18, 7, 0));
            wanderMateDao.insert_route_details(new RouteDetails(3, 20, 19, 7, 20));
            wanderMateDao.insert_route_details(new RouteDetails(3, 43, 20, 8, 5));
            wanderMateDao.insert_route_details(new RouteDetails(3, 22, 21, 8, 40));
            wanderMateDao.insert_route_details(new RouteDetails(3, 23, 22, 9, 15));
            wanderMateDao.insert_route_details(new RouteDetails(3, 39, 23, 10, 0));
            wanderMateDao.insert_route_details(new RouteDetails(3, 40, 24, 10, 25));
            wanderMateDao.insert_route_details(new RouteDetails(3, 41, 25, 10, 50));
            wanderMateDao.insert_route_details(new RouteDetails(3, 42, 26, 11, 10));
            wanderMateDao.insert_route_details(new RouteDetails(4, 6, 1, 0, 0));
            wanderMateDao.insert_route_details(new RouteDetails(4, 7, 2, 0, 40));
            wanderMateDao.insert_route_details(new RouteDetails(4, 8, 3, 1, 0));
            wanderMateDao.insert_route_details(new RouteDetails(4, 9, 4, 1, 15));
            wanderMateDao.insert_route_details(new RouteDetails(4, 10, 5, 1, 20));
            wanderMateDao.insert_route_details(new RouteDetails(4, 11, 6, 1, 45));
            wanderMateDao.insert_route_details(new RouteDetails(4, 12, 7, 2, 15));
            wanderMateDao.insert_route_details(new RouteDetails(4, 1, 8, 2, 30));
            wanderMateDao.insert_route_details(new RouteDetails(4, 2, 9, 2, 50));
            wanderMateDao.insert_route_details(new RouteDetails(4, 3, 10, 3, 5));
            wanderMateDao.insert_route_details(new RouteDetails(4, 4, 11, 3, 15));
            wanderMateDao.insert_route_details(new RouteDetails(4, 5, 12, 3, 35));
            wanderMateDao.insert_route_details(new RouteDetails(4, 13, 13, 4, 25));
            wanderMateDao.insert_route_details(new RouteDetails(4, 14, 14, 5, 10));
            wanderMateDao.insert_route_details(new RouteDetails(4, 15, 15, 5, 30));
            wanderMateDao.insert_route_details(new RouteDetails(4, 16, 16, 6, 0));
            wanderMateDao.insert_route_details(new RouteDetails(4, 17, 17, 6, 20));
            wanderMateDao.insert_route_details(new RouteDetails(4, 18, 18, 6, 40));
            wanderMateDao.insert_route_details(new RouteDetails(4, 19, 19, 7, 0));
            wanderMateDao.insert_route_details(new RouteDetails(4, 20, 20, 7, 20));
            wanderMateDao.insert_route_details(new RouteDetails(4, 21, 21, 8, 20));
            wanderMateDao.insert_route_details(new RouteDetails(4, 22, 22, 8, 35));
            wanderMateDao.insert_route_details(new RouteDetails(4, 23, 23, 9, 10));
            wanderMateDao.insert_route_details(new RouteDetails(4, 44, 24, 9, 40));
            wanderMateDao.insert_route_details(new RouteDetails(5, 6, 1, 0, 0));
            wanderMateDao.insert_route_details(new RouteDetails(5, 25, 2, 0, 25));
            wanderMateDao.insert_route_details(new RouteDetails(5, 26, 3, 0, 45));
            wanderMateDao.insert_route_details(new RouteDetails(5, 27, 4, 1, 15));
            wanderMateDao.insert_route_details(new RouteDetails(5, 28, 5, 1, 20));
            wanderMateDao.insert_route_details(new RouteDetails(5, 29, 6, 1, 40));
            wanderMateDao.insert_route_details(new RouteDetails(5, 30, 7, 2, 25));
            wanderMateDao.insert_route_details(new RouteDetails(5, 31, 8, 2, 40));
            wanderMateDao.insert_route_details(new RouteDetails(5, 32, 9, 2, 45));
            wanderMateDao.insert_route_details(new RouteDetails(5, 33, 10, 3, 5));
            wanderMateDao.insert_route_details(new RouteDetails(5, 34, 11, 3, 25));
            wanderMateDao.insert_route_details(new RouteDetails(5, 35, 12, 3, 50));
            wanderMateDao.insert_route_details(new RouteDetails(5, 36, 13, 4, 25));
            wanderMateDao.insert_route_details(new RouteDetails(5, 37, 14, 5, 20));
            wanderMateDao.insert_route_details(new RouteDetails(5, 38, 15, 6, 5));
            wanderMateDao.insert_route_details(new RouteDetails(5, 17, 16, 6, 25));
            wanderMateDao.insert_route_details(new RouteDetails(5, 18, 17, 6, 45));
            wanderMateDao.insert_route_details(new RouteDetails(5, 19, 18, 7, 5));
            wanderMateDao.insert_route_details(new RouteDetails(5, 20, 19, 7, 25));
            wanderMateDao.insert_route_details(new RouteDetails(5, 21, 20, 8, 20));
            wanderMateDao.insert_route_details(new RouteDetails(5, 22, 21, 8, 35));
            wanderMateDao.insert_route_details(new RouteDetails(5, 23, 22, 9, 10));
            wanderMateDao.insert_route_details(new RouteDetails(5, 44, 23, 9, 40));

            wanderMateDao.insert_bus(new Bus(1, "Super Fast"));
            wanderMateDao.insert_bus(new Bus(2, "Fast Passenger"));

            wanderMateDao.insert_service(new Service(1, 1, 8, 0));
            wanderMateDao.insert_service(new Service(1, 2, 23, 0));
            wanderMateDao.insert_service(new Service(2, 1, 21, 30));
            wanderMateDao.insert_service(new Service(3, 1, 8, 45));
            wanderMateDao.insert_service(new Service(4, 1, 9, 0));
            wanderMateDao.insert_service(new Service(2, 1, 11, 30));
            wanderMateDao.insert_service(new Service(5, 1, 11, 20));

            return null;
        }
    }
}
