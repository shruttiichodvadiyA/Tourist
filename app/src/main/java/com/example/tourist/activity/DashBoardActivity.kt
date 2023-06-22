package com.example.tourist.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.tourist.Fragment.ExploreFragment
import com.example.tourist.Fragment.HomeFragment
import com.example.tourist.Fragment.LocationFragment
import com.example.tourist.Fragment.WishlistFragment
import com.example.tourist.R
import com.example.tourist.databinding.ActivityDashBoardBinding
import com.example.tourist.ModelClass.ImagesModelClass
import com.example.tourist.PaymentActivity
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference

class DashBoardActivity : AppCompatActivity() {
    lateinit var binding: ActivityDashBoardBinding
    private lateinit var auth: FirebaseAuth
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var storageReference: StorageReference
    lateinit var sharedPreferences: SharedPreferences
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var imageslist: ImagesModelClass
    var images = intArrayOf(
        R.drawable.dubai, R.drawable.singapore, R.drawable.indiaaaaaa, R.drawable.london,
        R.drawable.germany, R.drawable.maldivas
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        firebaseAuth = Firebase.auth
        firebaseAuth = FirebaseAuth.getInstance()
        initView()
        logout()
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val myEdit: SharedPreferences.Editor = sharedPreferences.edit()
            myEdit.remove("isLogin")
            myEdit.commit()

            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
    }


    private fun initView() {
        binding.lnrbooking.setOnClickListener {
            val i = Intent(this@DashBoardActivity, PaymentActivity::class.java)
            startActivity(i)
        }
        //version
        val manager=packageManager
        val info: PackageInfo =manager.getPackageInfo(
            packageName,0
        )
        val version:String=info.versionName
        binding.txtversion.text=version

        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE)

        binding.btnmenu.setOnClickListener {
            binding.navigationDrawer.openDrawer(GravityCompat.START)
        }
        loadfragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener ( object :NavigationView.OnNavigationItemSelectedListener,NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var fragment: Fragment
                when (item.itemId) {
                    R.id.btnhome -> {
                        fragment = HomeFragment()
                        loadfragment(fragment)
                    }
                    R.id.btnexplore -> {
                        fragment = ExploreFragment()
                        loadfragment(fragment)
                    }
                    R.id.btnlocation -> {
                        fragment = LocationFragment()
                        loadfragment(fragment)
                    }
                    R.id.btnwishlist -> {
                        fragment = WishlistFragment()
                        loadfragment(fragment)
                    }

                }
                return true
            }
        })
    }

    fun loadfragment(f: Fragment) {
        val fm: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, f)
        fragmentTransaction.commit()
    }

}