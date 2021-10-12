package com.example.newmovlex.ui.models

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.newmovlex.R
import kotlinx.android.synthetic.main.custom_dilog.*

class CustomDialog : DialogFragment() {

    private var msg: String? = null
    private var title: String? = null
    private var btnOkay: String? = null
    private var btnCancel: String? = null
    private var listener: CustomDialogListener? = null

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()
        dialog?.window!!.setBackgroundDrawable(ColorDrawable(R.color.bg))
    }

    fun newInstance(
        title: String,
        msg: String,
        btnOk: String,
        btnCancel: String
    ): CustomDialog {
        val args = Bundle()
        args.putString("title", title)
        args.putString("msg", msg)
        args.putString("btnOk", btnOk)
        args.putString("btnCancel", btnCancel)
        val fragment = CustomDialog()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.custom_dilog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        if (!TextUtils.isEmpty(title)) {
            tv_alert_title.visibility = View.VISIBLE
            tv_alert_title.text = title
        } else {
            tv_alert_title.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(msg)) {
            tv_alert_content.visibility = View.VISIBLE
            tv_alert_content.text = msg
        } else {
            tv_alert_content.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(btnOkay)) {
            btn_alert_accept.visibility = View.VISIBLE
            btn_alert_accept.text = btnOkay
        } else {
            btn_alert_accept.visibility = View.GONE
        }
        if (!TextUtils.isEmpty(btnCancel)) {
            btn_alert_cancel.visibility = View.VISIBLE
            btn_alert_cancel.text = btnCancel
        } else {
            btn_alert_cancel.visibility = View.GONE
        }
//        problem : how to set listener to button
        btn_alert_accept.setOnClickListener {
            listener?.onDialogPositiveClick("Success")
        }
        btn_alert_cancel.setOnClickListener {
            listener?.onDialogNegativeClick("Failed")
        }


    }

    fun onClickListener(listener: CustomDialogListener) {
        this.listener = listener
    }


    interface CustomDialogListener {
        fun onDialogPositiveClick(str: String)
        fun onDialogNegativeClick(str: String)
    }

    private fun getArgs() {
        arguments?.getString("title")?.let {
            title = it;
        }
        arguments?.getString("msg")?.let {
            msg = it;
        }
        arguments?.getString("btnOk")?.let {
            btnOkay = it;
        }
        arguments?.getString("btnCancel")?.let {
            btnCancel = it;
        }

    }
}



