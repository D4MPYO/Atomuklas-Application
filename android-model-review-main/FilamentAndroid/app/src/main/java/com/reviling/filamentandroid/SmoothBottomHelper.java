package com.reviling.filamentandroid;

import android.app.Activity;
import android.content.Intent;


public class SmoothBottomHelper {

//    public static void setupSmoothBottomBar(Activity activity, SmoothBottomBar bottomBar) {
//        bottomBar.setOnItemSelectedListener(pos -> {
//            switch (pos) {
//                case 0:
//                    if (!(activity instanceof MainActivity)) {
//                        activity.startActivity(new Intent(activity, MainActivity.class));
//                        activity.overridePendingTransition(0, 0); // optional: no animation
//                        activity.finish();
//                    }
//                    break;
//                case 1:
//                    if (!(activity instanceof Library)) {
//                        activity.startActivity(new Intent(activity, Library.class));
//                        activity.overridePendingTransition(0, 0);
//                        activity.finish();
//                    }
//                    break;
//                case 2:
//                    if (!(activity instanceof Quizzes)) {
//                        activity.startActivity(new Intent(activity, Quizzes.class));
//                        activity.overridePendingTransition(0, 0);
//                        activity.finish();
//                    }
//                    break;
//                case 3:
//                    if (!(activity instanceof Accomplishment)) {
//                        activity.startActivity(new Intent(activity, Accomplishment.class));
//                        activity.overridePendingTransition(0, 0);
//                        activity.finish();
//                    }
//                    break;
//                case 4:
//                    if (!(activity instanceof Profile)) {
//                        activity.startActivity(new Intent(activity, Profile.class));
//                        activity.overridePendingTransition(0, 0);
//                        activity.finish();
//                    }
//                    break;
//            }
//            return false;
//        });
//    }
//
//    public static void highlightCurrentTab(Activity activity, SmoothBottomBar bottomBar) {
//        int currentIndex = 0;
//
//        if (activity instanceof MainActivity) {
//            currentIndex = 0;
//        } else if (activity instanceof Library) {
//            currentIndex = 1;
//        } else if (activity instanceof Quizzes) {
//            currentIndex = 2;
//        } else if (activity instanceof Accomplishment) {
//            currentIndex = 3;
//        } else if (activity instanceof Profile) {
//            currentIndex = 4;
//        }
//
//        bottomBar.setItemActiveIndex(currentIndex);
//    }
}
