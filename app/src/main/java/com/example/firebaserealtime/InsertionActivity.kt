package com.example.firebaserealtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_insertion.*

class InsertionActivity : AppCompatActivity() {
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
//        xu ly su kien khi nhan save
        btnSave.setOnClickListener {
            saveEmployeeData()
        }

    }

    private fun saveEmployeeData() {
//        get value
        val empName = edtEmpName.text.toString()
        val empAge = edtEmpAge.text.toString()
        val empSalary = edtEmpSalary.text.toString()
//        day du lieu len firebase
        val empId = dbRef.push().key!!
        val employee = EmployeeModel(empId, empName, empAge, empSalary)

//        kiem tra cac o nhap lieu da co du lieu hay
        if(empName.isEmpty()){
            edtEmpName.error = "Please enter a name"
            return
        }
        if(empAge.isEmpty()){
            edtEmpAge.error = "Please enter a Age"
            return
        }
        if(empSalary.isEmpty()){
            edtEmpSalary.error = "Please enter a salary"
            return
        }
        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted complete", Toast.LENGTH_SHORT).show()
                edtEmpName.setText("")
                edtEmpAge.setText("")
                edtEmpSalary.setText("")
            }
            .addOnFailureListener { err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_SHORT).show()
            }

    }
}

