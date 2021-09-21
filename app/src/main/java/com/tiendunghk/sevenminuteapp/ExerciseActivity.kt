package com.tiendunghk.sevenminuteapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiendunghk.sevenminuteapp.models.ExerciseModel
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseTimerDuration: Long = 30

    private var exercisesList: ArrayList<ExerciseModel>? = null
    private var currentExercisePostion = -1

    private var tts: TextToSpeech? = null

    private var player: MediaPlayer? = null

    private var exerciseStatusAdapter: ExerciseStatusAdapter? = null

    private var toolBar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        toolBar = findViewById(R.id.toolbar_exercise_activity)
        setSupportActionBar(toolBar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolBar!!.setNavigationOnClickListener {
            onBackPressed()
        }

        tts = TextToSpeech(this, this)

        exercisesList = Constants.defaultExercisesList()
        setupRestView()

        setupExerciseStatusRCV()
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }

        if (player != null) {
            player!!.stop()
        }
        super.onDestroy()
    }

    private fun setRestProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvTimer = findViewById<TextView>(R.id.tvTimer)

        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePostion++

                exercisesList!![currentExercisePostion].setIsSelected(true)

                exerciseStatusAdapter!!.notifyDataSetChanged()

                setupExerciseView()
            }
        }.start()

        progressBar.progress = restProgress
    }

    private fun setupRestView() {
        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)

            player!!.isLooping = false
            player!!.start()
        } catch (e: Exception) {

        }


        val llRestView = findViewById<LinearLayout>(R.id.llRestView)
        val llExerciseView = findViewById<LinearLayout>(R.id.llExerciseView)

        val tvUpcomingExerciseName = findViewById<TextView>(R.id.tvUpcomingExerciseName)
        tvUpcomingExerciseName.text = exercisesList!![currentExercisePostion + 1].getName()

        llRestView.visibility = View.VISIBLE
        llExerciseView.visibility = View.GONE

        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }

    private fun setExerciseProgressBar() {
        val progressBarExercise = findViewById<ProgressBar>(R.id.progressBarExercise)
        val tvExerciseTimer = findViewById<TextView>(R.id.tvExerciseTimer)

        exerciseTimer = object : CountDownTimer(exerciseTimerDuration * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = exerciseTimerDuration.toInt() - exerciseProgress
                tvExerciseTimer.text = (exerciseTimerDuration.toInt() - exerciseProgress).toString()
            }

            override fun onFinish() {
                //exercisesList?.size!! - 1
                if (currentExercisePostion < exercisesList!!.size) {
                    exercisesList!![currentExercisePostion].setIsSelected(false)
                    exercisesList!![currentExercisePostion].setIsCompleted(true)
                    exerciseStatusAdapter?.notifyDataSetChanged()

                    setupRestView()
                } else {
                    val finishIntent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(finishIntent)
                    finish()
                }
            }
        }.start()

        progressBarExercise.progress = exerciseProgress
    }

    private fun setupExerciseView() {
        val llRestView = findViewById<LinearLayout>(R.id.llRestView)
        val llExerciseView = findViewById<LinearLayout>(R.id.llExerciseView)
        llRestView.visibility = View.GONE
        llExerciseView.visibility = View.VISIBLE
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        speakOut(exercisesList!![currentExercisePostion].getName())
        setExerciseProgressBar()

        val ivImage = findViewById<ImageView>(R.id.ivImage)
        val tvExerciseName = findViewById<TextView>(R.id.tvExerciseName)

        ivImage.setImageResource(exercisesList!![currentExercisePostion].getImage())
        tvExerciseName.text = exercisesList!![currentExercisePostion].getName()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The language is not supported!")
            }
        } else {
            Log.e("TTS", "Initialized error")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun setupExerciseStatusRCV() {
        val rcvStatus = findViewById<RecyclerView>(R.id.rvExerciseStatus)

        rcvStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        exerciseStatusAdapter = ExerciseStatusAdapter(exercisesList!!, this)

        rcvStatus.adapter = exerciseStatusAdapter
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        customDialog.setContentView(R.layout.dialog_custom_back_confirmation)

        val tvYes = customDialog.findViewById<Button>(R.id.tvYes)
        val tvNo = customDialog.findViewById<Button>(R.id.tvNo)

        tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }

        tvNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }
}
