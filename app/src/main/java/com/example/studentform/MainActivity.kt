package com.example.studentform

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.CheckBox
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.studentform.Utils.Utils
import com.example.studentform.helper.AddressHelper
import java.util.Calendar

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

    private lateinit var rb_female: RadioButton
    private lateinit var rb_male: RadioButton

    private lateinit var spn_district: Spinner
    private lateinit var spn_province: Spinner
    private lateinit var spn_ward: Spinner

    private lateinit var tv_birth: TextView
    private lateinit var tv_notification: TextView

    private lateinit var provinces: List<String>
    private var districts: List<String> = ArrayList()
    private var wards: List<String> = ArrayList()

    private var selectedDistrict: String = ""
    private var selectedProvince: String = ""
    private var selectedWard: String = ""

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
        Log.d("CONSOLE: ", "" + rb_male.isSelected())
        Log.d("CONSOLE: ", "" + rb_female.isSelected())

        if (et_id.text.isNullOrBlank()) {
            tv_notification.text = "Mã số sinh viên không được để trống"
        }  else if (et_fullname.text.isNullOrBlank()) {
            tv_notification.text = "Họ tên không được để trống"
        } else if (!rb_male.isChecked && !rb_female.isChecked) {
            tv_notification.text = "Bạn chưa chọn giới tính"
        } else if (et_email.text.isNullOrBlank()) {
            tv_notification.text = "Email không được để trống"
        } else if (et_phonenumber.text.isNullOrBlank()) {
            tv_notification.text = "Số điện thoại khÔng được để trống"
        } else if (tv_birth.text == "dd/MM/yyyy") {
            tv_notification.text = "Bạn chưa chọn ngày sinh"
        } else if (!cb_cinema.isChecked && !cb_music.isChecked && !cb_sport.isChecked) {
            tv_notification.text = "Hãy chọn ít nhất 1 sở thích"
        } else if (!cb_agree.isChecked) {
            tv_notification.text = "Hãy đồng ý với Điều khoản và dịch vụ"
        } else if (!Utils.checkEmail(et_email.text.toString())) {
            tv_notification.text = "Email không hợp lệ"
        } else if (!Utils.checkPhoneNumber(et_phonenumber.text.toString())) {
            tv_notification.text = "Số điện thoại không hợp lệ"
        } else if (!Utils.checkFullName(et_fullname.text.toString())) {
            tv_notification.text = "Họ và tên không hợp lệ"
        } else {
            tv_notification.text = ""
        }
    }

    fun showCalendar() {
        puw_calendar = PopupWindow(popupView, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT, true)
        puw_calendar.showAtLocation(popupView, Gravity.CENTER, 0, 0)
    }

    fun getComponent() {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        popupView = inflater.inflate(R.layout.popup_calendar, null)

        val addressHelper = AddressHelper(resources)

        btn_select_birth = findViewById(R.id.btn_select_birth)
        btn_submit = findViewById(R.id.btn_submit)

        cb_agree = findViewById(R.id.cb_agree)
        cb_cinema = findViewById(R.id.cb_cinema)
        cb_music = findViewById(R.id.cb_music)
        cb_sport = findViewById(R.id.cb_sport)

        cv_select_birth = popupView.findViewById(R.id.cv_select_birth)
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -18)
        cv_select_birth.date = calendar.timeInMillis

        et_id = findViewById(R.id.et_id)
        et_fullname = findViewById(R.id.et_fullname)
        et_email = findViewById(R.id.et_email)
        et_phonenumber = findViewById(R.id.et_phonenumber)

        rb_female = findViewById(R.id.rb_female)
        rb_male = findViewById(R.id.rb_male)

        spn_district = findViewById(R.id.spn_district)
        spn_province = findViewById(R.id.spn_province)
        spn_ward = findViewById(R.id.spn_ward)

        tv_birth = findViewById(R.id.tv_birth)
        tv_notification = findViewById(R.id.tv_notification)

        cv_select_birth.setOnDateChangeListener { _, year, month, day ->
            tv_birth.setText("$day/${month + 1}/$year")
        }

        provinces = addressHelper.getProvinces()
        spn_province.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        spn_province.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedProvince = provinces[p2]
                selectedDistrict = ""
                selectedWard = ""

                districts = addressHelper.getDistricts(selectedProvince)
                spn_district.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, districts)
                spn_district.setSelection(-1, true)
                spn_ward.adapter = null
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        spn_district.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedDistrict = districts[p2]
                selectedWard = ""

                wards = addressHelper.getWards(selectedProvince, selectedDistrict)
                spn_ward.adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, wards)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        spn_ward.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedWard = wards[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }
}