package com.attendo.ui.splash

import android.app.ActionBar
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.attendo.R
import com.attendo.ui.auth.login.FragmentLogin
import com.attendo.ui.main.BottomNavMainActivity
import com.google.android.material.transition.MaterialSharedAxis
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

     lateinit var splashIcon:ImageView
     lateinit var actionbar:ActionBar



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        splashIcon = view.findViewById<ImageView>(R.id.splash_screen_app_icon)
        val animation =
            AnimationUtils.loadAnimation(context, R.anim.bounce)

        splashIcon.setAnimation(animation)
        val user = FirebaseAuth.getInstance().currentUser
        Handler().postDelayed({
            if (user != null) {
                val i = Intent(activity, BottomNavMainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                val fragmentLogin = FragmentLogin()
                fragmentLogin.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
                setFragment(fragmentLogin)
            }
        }, 1000)
        return view

    }
    private fun setFragment(fragment: Fragment) {
        try {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.start_frame, fragment)
            fragmentTransaction.commit()
        } catch (e: Exception) {
            Log.e("Exception", e.message!!)
        }
    }




}