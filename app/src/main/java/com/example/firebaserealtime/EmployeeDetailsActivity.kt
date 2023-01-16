package com.example.firebaserealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_employee_details.*

class EmployeeDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        setValueToView()
        //code cho button delete
        btnDelete.setOnClickListener {
            //alt + enter
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }
        //code cho button update
        btnUpdate.setOnClickListener {
            //alt + enter
            openUpdateDialog(
                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empName").toString()
            )
        }
    }

    private fun openUpdateDialog(empId: String, empName: String) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog,null)
        mDialog.setView(mDialogView)
        //update thông tin vào dialog
        val etEmpName = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etEmpAge = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etEmpSalary = mDialogView.findViewById<EditText>(R.id.etEmpSalary)
        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpName.setText(intent.getStringExtra("empName").toString())
        etEmpAge.setText(intent.getStringExtra("empAge").toString())
        etEmpSalary.setText(intent.getStringExtra("empSalary").toString())

        mDialog.setTitle("Updating $empName Record")
        val alertDialog = mDialog.create()
        alertDialog.show()

        //clcik vào btnUpdateData
        btnUpdateData.setOnClickListener {
            updateEmpData(
                empId,
                etEmpName.text.toString(),
                etEmpAge.text.toString(),
                etEmpSalary.text.toString()
            )
            Toast.makeText(applicationContext,"Employee Data updated",Toast.LENGTH_SHORT).show()
            //update lại data lên hộp thoại dialog
            tvEmpName.setText(etEmpName.text.toString())
            tvEmpAge.setText(etEmpAge.text.toString())
            tvEmpSalary.setText(etEmpSalary.text.toString())
            alertDialog.dismiss()

        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        age: String,
        salary: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val empInfo = EmployeeModel(id,name,age,salary)
        dbRef.setValue(empInfo)

    }


    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Employees").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this,"Employee data đã xoá",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener { err->
            Toast.makeText(this,"Delete err ${err.message}",Toast.LENGTH_SHORT).show()
        }

    }

    private fun setValueToView() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpAge.text = intent.getStringExtra("empAge")
        tvEmpName.text = intent.getStringExtra("empName")
        tvEmpSalary.text = intent.getStringExtra("empSalary")
    }
}