/*
 * Nguyen Tien Dung
 * dunghkuit@gmail.com
 * UIT
 */

package com.tiendunghk.sevenminuteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    private val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    private val US_UNITS_VIEW = "US_UNIT_VIEw"

    private var toolBar: Toolbar? = null
    private var btnSubmit: Button? = null
    private var etMetricUnitWeight: EditText? = null
    private var etMetricUnitHeight: EditText? = null
    private var llDisplayBMIResult: LinearLayout? = null

    private var etUSUnitsHeightFeet: EditText? = null
    private var etUSUnitsHeightInch: EditText? = null
    private var etUSUnitsWeight: EditText? = null

    private var llUsUnitsView: LinearLayout? = null
    private var llMetricUnitsView: LinearLayout? = null


    private var etUsUnitWeight: EditText? = null
    private var etUsUnitHeightFeet: EditText? = null
    private var etUsUnitHeightInch: EditText? = null

    private var rgUnits: RadioGroup? = null

    private var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmiactivity)

        toolBar = findViewById(R.id.toolbar_bmi_activity)
        setSupportActionBar(toolBar)

        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = "CALCULATE BMI"

        toolBar?.setNavigationOnClickListener { onBackPressed() }

        etMetricUnitWeight = findViewById(R.id.etMetricUnitWeight)
        etMetricUnitHeight = findViewById(R.id.etMetricUnitHeight)
        llDisplayBMIResult = findViewById(R.id.llDiplayBMIResult)

        etUSUnitsHeightFeet = findViewById(R.id.etUsUnitHeightFeet)
        etUSUnitsHeightInch = findViewById(R.id.etUsUnitHeightInch)
        etUSUnitsWeight = findViewById(R.id.etUsUnitWeight)

        llUsUnitsView = findViewById(R.id.llUsUnitsView)
        llMetricUnitsView = findViewById(R.id.llMetricUnitsView)
        btnSubmit = findViewById(R.id.btnCalculateUnits)

        rgUnits = findViewById(R.id.rgUnits)



        btnSubmit?.setOnClickListener {
            if (currentVisibleView == METRIC_UNITS_VIEW) {
                if (validateMetricUnits()) {
                    val heightValue: Float = etMetricUnitHeight?.text.toString().toFloat() / 100
                    val weightValue: Float = etMetricUnitWeight?.text.toString().toFloat()

                    val bmi = weightValue / (heightValue * heightValue)

                    displayBMIResult(bmi)
                }

                makeVisibleMetricUnitsView()

                rgUnits?.setOnCheckedChangeListener { _, checkedId ->
                    if (checkedId == R.id.rbMetricUnits) {
                        makeVisibleMetricUnitsView()
                    } else {
                        makeVisibleUSUnitsView()
                    }
                }
            } else {
                if (validateUSUnits()) {
                    val usUnitHeightValueFeet: String = etUsUnitHeightFeet?.text.toString()
                    val usUnitHeightValueInch: String = etUsUnitHeightInch?.text.toString()
                    val usUnitWeightValue: Float = etUsUnitWeight?.text.toString().toFloat()

                    val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

                    val bmi = 703 * (usUnitWeightValue / (heightValue * heightValue))

                    displayBMIResult(bmi)
                }

                //makeVisibleMetricUnitsView()

            }
        }
    }


    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW

        etMetricUnitWeight?.text!!.clear()
        etMetricUnitHeight?.text!!.clear()

        llUsUnitsView?.visibility = View.VISIBLE
        llMetricUnitsView?.visibility = View.GONE

        llDisplayBMIResult?.visibility = View.GONE
    }

    private fun makeVisibleUSUnitsView() {
        currentVisibleView = US_UNITS_VIEW


        etUsUnitWeight?.text!!.clear()
        etUsUnitHeightFeet?.text!!.clear()
        etUsUnitHeightInch?.text!!.clear()

        llUsUnitsView?.visibility = View.GONE
        llMetricUnitsView?.visibility = View.VISIBLE

        llDisplayBMIResult?.visibility = View.GONE
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val tvYourBMI = findViewById<TextView>(R.id.tvYourBMI)
        val tvBMIValue = findViewById<TextView>(R.id.tvBMIValue)
        val tvBMIType = findViewById<TextView>(R.id.tvBMIType)
        val tvBMIDescription = findViewById<TextView>(R.id.tvBMIDescription)

        llDisplayBMIResult?.visibility = View.VISIBLE

        /*tvYourBMI.visibility = View.VISIBLE
        tvBMIValue.visibility = View.VISIBLE
        tvBMIType.visibility = View.VISIBLE
        tvBMIDescription.visibility = View.VISIBLE*/


        // This is used to round of the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        tvBMIValue.text = bmiValue // Value is set to TextView
        tvBMIType.text = bmiLabel // Label is set to TextView
        tvBMIDescription.text = bmiDescription // Description is set to TextView
    }


    private fun validateMetricUnits(): Boolean {
        var isValid = true



        if (etMetricUnitWeight?.text.toString().isEmpty()) {
            isValid = false
        } else if (etMetricUnitHeight?.text.toString().isEmpty()) {
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits(): Boolean {
        var isValid = true



        when {
            etUSUnitsHeightFeet?.text.toString().isEmpty() -> isValid = false
            etUSUnitsHeightInch?.text.toString().isEmpty() -> isValid = false
            etUSUnitsWeight?.text.toString().isEmpty() -> isValid = false
        }

        return isValid
    }
}
