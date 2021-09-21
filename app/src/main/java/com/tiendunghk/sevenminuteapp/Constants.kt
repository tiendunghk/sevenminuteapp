/*
 * Nguyen Tien Dung
 * dunghkuit@gmail.com
 * UIT
 */


package com.tiendunghk.sevenminuteapp

import com.tiendunghk.sevenminuteapp.models.ExerciseModel

class Constants {
    companion object {
        fun defaultExercisesList(): ArrayList<ExerciseModel> {
            val exercisesList = ArrayList<ExerciseModel>()

            val jumpingJacks =
                ExerciseModel(1, "Jumping Jacks", R.drawable.ic_jumping_jacks, false, false)
            exercisesList.add(jumpingJacks)

            val wallSit = ExerciseModel(2, "Wall Sit", R.drawable.ic_wall_sit, false, false)
            exercisesList.add(wallSit)

            val pushUp = ExerciseModel(3, "Push Up", R.drawable.ic_push_up, false, false)
            exercisesList.add(pushUp)

            val abdominalCrunch =
                ExerciseModel(4, "Abdominal Crunch", R.drawable.ic_abdominal_crunch, false, false)
            exercisesList.add(abdominalCrunch)

            val stepUpOnChair =
                ExerciseModel(
                    5,
                    "Step-Up onto Chair",
                    R.drawable.ic_step_up_onto_chair,
                    false,
                    false
                )
            exercisesList.add(stepUpOnChair)

            val squat = ExerciseModel(6, "Squat", R.drawable.ic_squat, false, false)
            exercisesList.add(squat)

            val tricepDipOnChair =
                ExerciseModel(
                    7,
                    "Tricep Dip On Chair",
                    R.drawable.ic_triceps_dip_on_chair,
                    false,
                    false
                )
            exercisesList.add(tricepDipOnChair)

            val plank = ExerciseModel(8, "Plank", R.drawable.ic_plank, false, false)
            exercisesList.add(plank)

            val highKneesRunningInPlace =
                ExerciseModel(
                    9, "High Knees Running In Place",
                    R.drawable.ic_high_knees_running_in_place,
                    false,
                    false
                )
            exercisesList.add(highKneesRunningInPlace)

            val lunges = ExerciseModel(10, "Lunges", R.drawable.ic_lunge, false, false)
            exercisesList.add(lunges)

            val pushupAndRotation =
                ExerciseModel(
                    11,
                    "Push up and Rotation",
                    R.drawable.ic_push_up_and_rotation,
                    false,
                    false
                )
            exercisesList.add(pushupAndRotation)

            val sidePlank = ExerciseModel(12, "Side Plank", R.drawable.ic_side_plank, false, false)
            exercisesList.add(sidePlank)

            return exercisesList
        }
    }
}
