package com.example.firebaserealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaserealtime.adapter.EmpAdapter
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_fetching.*
import kotlinx.android.synthetic.main.activity_insertion.*

class FetchingActivity : AppCompatActivity() {
    private lateinit var ds: ArrayList<EmployeeModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        rvEmp.layoutManager = LinearLayoutManager(this)
        rvEmp.setHasFixedSize(true)
        ds = arrayListOf<EmployeeModel>()
        GetThongTin()
    }

    private fun GetThongTin() {
        rvEmp.visibility = View.GONE
        txtLoadingData.visibility = View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
//                snapshot kiem tra bang du lieu
                ds.clear()
                if(snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(EmployeeModel::class.java)
                        ds.add(empData!!)
                    }
                    val mAdapter = EmpAdapter(ds)
                    rvEmp.adapter = mAdapter
//                    code lang nghe su kien click len iten rvEmp
                    mAdapter.setOnItemClickListener(object : EmpAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity, EmployeeDetailsActivity::class.java)
//                            put extras
                            intent.putExtra("empId",ds[position].empId)
                            intent.putExtra("empName",ds[position].empName)
                            intent.putExtra("empAge",ds[position].empAge)
                            intent.putExtra("empSalary",ds[position].empSalary)

                            startActivity(intent)
                        }
                    })

                    rvEmp.visibility = View.VISIBLE
                    txtLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}