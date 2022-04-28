package com.chathuranga.minicalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var tvOutput : TextView? = null
    var  lastNumeric : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvOutput = findViewById(R.id.tvOutput)
    }
    fun onDigit(view: View){
        tvOutput?.append((view as Button).text)
        lastNumeric = true
    }
    fun onClear(view: View){
        tvOutput?.text = " "
        lastNumeric = false
    }
    fun onDecimalPoint(view: View){
        if(lastNumeric  && !lastDot){
            tvOutput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onOperator(view: View){
        tvOutput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvOutput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }
    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue = tvOutput?.text.toString()
            var prefix = ""
            try {

                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one  = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvOutput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                }else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one  = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvOutput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                }else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one  = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvOutput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                }else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one  = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }

                    tvOutput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                }

            }catch (e : ArithmeticException){
                e.printStackTrace()
            }
        }
        }

    private fun removeZeroAfterDot(result : String) : String {
        var value = result
         if(result.contains(".0"))
             value = result.substring(0, result.length-2)

        return value
    }

    private fun isOperatorAdded(value : String) : Boolean{
        return if(value.startsWith("-")){
            return false
        }else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}