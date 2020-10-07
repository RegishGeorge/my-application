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
            wanderMateDao.insert_route(new Route("PDM-KTM", 5));
            wanderMateDao.insert_route(new Route("TVM-PNM", 23));

            wanderMateDao.insert_stop(new Stop("Pandalam", 9.225407, 76.674565));
            wanderMateDao.insert_stop(new Stop("Chengannur", 9.318343, 76.612990));
            wanderMateDao.insert_stop(new Stop("Thiruvalla", 9.386564, 76.574473));
            wanderMateDao.insert_stop(new Stop("Changanassery", 9.445678, 76.540909));
            wanderMateDao.insert_stop(new Stop("Kottayam", 9.585719, 76.523438));
            wanderMateDao.insert_stop(new Stop("Thiruvananthapuram", 8.488315, 76.952160));
            wanderMateDao.insert_stop(new Stop("Venjaramoodu", 8.677908, 76.910470));
            wanderMateDao.insert_stop(new Stop("Kilimanoor", 8.774899, 76.880560));
            wanderMateDao.insert_stop(new Stop("Chadayamangalam", 8.872400, 76.869109));
            wanderMateDao.insert_stop(new Stop("Ayoor", 8.898567, 76.859699));
            wanderMateDao.insert_stop(new Stop("Kottarakkara", 9.006061, 76.781918));
            wanderMateDao.insert_stop(new Stop("Adoor", 9.152839, 76.733111));
            wanderMateDao.insert_stop(new Stop("Ettumanoor", 9.667796, 76.555942));
            wanderMateDao.insert_stop(new Stop("Koothattukulam", 9.863363, 76.592747));
            wanderMateDao.insert_stop(new Stop("Muvattupuzha", 9.977397, 76.579660));
            wanderMateDao.insert_stop(new Stop("Perumbavoor", 10.110964, 76.482716));
            wanderMateDao.insert_stop(new Stop("Angamaly", 10.195806, 76.386338));
            wanderMateDao.insert_stop(new Stop("Chalakudy", 10.301593, 76.332938));
            wanderMateDao.insert_stop(new Stop("Puthukkad", 10.425747, 76.268114));
            wanderMateDao.insert_stop(new Stop("Thrissur", 10.517526, 76.210430));
            wanderMateDao.insert_stop(new Stop("Shornur", 10.763239, 76.269906));
            wanderMateDao.insert_stop(new Stop("Pattambi", 10.801734, 76.179911));
            wanderMateDao.insert_stop(new Stop("Perinthalmanna", 10.975154, 76.229953));
            wanderMateDao.insert_stop(new Stop("Indira Junction", 9.222572, 76.725064));


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

            wanderMateDao.insert_bus(new Bus("Super Fast"));
            wanderMateDao.insert_bus(new Bus("Fast Passenger"));

            wanderMateDao.insert_service(new Service(1,1, 8, 0));
            wanderMateDao.insert_service(new Service(1, 2,23, 0));
            wanderMateDao.insert_service(new Service(2,1,21,30));
            return null;
        }
    }
}
