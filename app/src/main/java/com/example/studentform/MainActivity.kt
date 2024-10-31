package com.example.studentform

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {
    private lateinit var btn_select_birth: Button
    private lateinit var btn_submit: Button

    private lateinit var cb_agree: CheckBox
    private lateinit var cb_cinema: CheckBox
    private lateinit var cb_music: CheckBox
    private lateinit var cb_sport: CheckBox

    private lateinit var cv_select_birth: CalendarView

    private lateinit var et_id: EditText
    private lateinit var et_fullname: EditText
    private lateinit var et_email: EditText
    private lateinit var et_phonenumber: EditText

    private lateinit var popupView: View

    private lateinit var puw_calendar: PopupWindow

    private lateinit var tv_birth: TextView
    private lateinit var tv_notification: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getComponent()

        btn_select_birth.setOnClickListener {
            showCalendar()
        }

        btn_submit.setOnClickListener {
            submitOnClick()
        }
    }

    fun submitOnClick() {
        if (et_id.text.isNullOrBlank()) {
            tv_notification.setText("Mã số sinh viên không được để trống")
        } else if (et_email.text.isNullOrBlank()) {
            tv_notification.setText("Email khÔng được để trống")
        } else if (et_fullname.text.isNullOrBlank()) {
            tv_notification.setText("Họ tên khÔng được để trống")
        } else if (et_phonenumber.text.isNullOrBlank()) {
            tv_notification.setText("Số điện thoại khÔng được để trống")
        }
    }

    fun showCalendar() {
        puw_calendar = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)
        puw_calendar.showAtLocation(popupView, Gravity.CENTER, 0, 0)
    }

    fun getComponent() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        popupView = inflater.inflate(R.layout.popup_calendar, null)

        btn_select_birth = findViewById(R.id.btn_select_birth)
        btn_submit = findViewById(R.id.btn_submit)

        cb_agree = findViewById(R.id.cb_agree)
        cb_cinema = findViewById(R.id.cb_cinema)
        cb_music = findViewById(R.id.cb_music)
        cb_sport = findViewById(R.id.cb_sport)

        cv_select_birth = popupView.findViewById(R.id.cv_select_birth)

        et_id = findViewById(R.id.et_id)
        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_phonenumber = findViewById(R.id.et_phonenumber)

        tv_birth = findViewById(R.id.tv_birth)
        tv_notification = findViewById(R.id.tv_notification)

        cv_select_birth.setOnDateChangeListener { view, year, month, day ->
            tv_birth.setText("$day/${month + 1}/$year")
        }
    }
}