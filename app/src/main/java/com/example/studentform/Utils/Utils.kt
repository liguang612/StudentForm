package com.example.studentform.Utils

class Utils {
    companion object {
        fun checkEmail(email: String) : Boolean {
            if (email.indexOf('@') == -1) return false
            if (email[0] == '@') return false
            if (email[email.length - 1] == '@') return false
            if (email.lastIndexOf('.') < email.indexOf('@')) return false
            if (email.indexOf('@') != email.lastIndexOf('@')) return false

            return true
        }

        fun checkPhoneNumber(phoneNumber: String) : Boolean {
            if (phoneNumber.length != 10) return false
            if (phoneNumber[0] != '0') return false
            for (i in phoneNumber) {
                if (!i.isDigit()) return false
            }

            return true
        }

        fun checkFullName(fullName: String) : Boolean {
            if (fullName.indexOf(' ') == -1) return false
            for (i in fullName) {
                if (i.isDigit()) return false
            }

            return true
        }
    }
}