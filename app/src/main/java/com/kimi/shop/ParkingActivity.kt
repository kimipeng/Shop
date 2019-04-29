package com.kimi.shop

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_parking.*
import org.jetbrains.anko.*
import java.net.URL

class ParkingActivity : AppCompatActivity(), AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)
        val parking =
            "http://data.tycg.gov.tw/opendata/datalist/datasetMeta/download?id=f4cc0b12-86ac-40f9-8745-885bddc18f79&rid=0daad6e6-0632-44f5-bd25-5e1de1e9146f"

        doAsync {
            val url = URL(parking)
            val json = url.readText()
            info { json }
            uiThread {
                toast("Got it")
                infor.text = json
                alert("Got it", "Alert") {
                    okButton {
                        parseGson(json)
                    }
                }.show()
            }
        }

        //ParkingTask().execute(parking)
    }

    private fun parseGson(json: String) {
        val parking = Gson().fromJson<Parking>(json, Parking::class.java)
        info { parking.parkingLots.size }
        parking.parkingLots.forEach {
            info ( "${it.areaId} ${it.areaName}" )
        }
    }

    inner class ParkingTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String {
            val url = URL(params[0])
            val json = url.readText()
            Log.d("kimi", "back: ${json}")

            return json
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            infor.text = result
        }
    }
}

//class Parking(val parkingLots: List<ParkingLot>)
//
//data class ParkingLot (
//    val areaId:String,
//    val areaName:String,
//    val parkName:String,
//    val totalSpace:Int
//)

/**
parkingLots" : [ {
"areaId" : "1",
"areaName" : "桃園區",
"parkName" : "府前地下停車場",
"totalSpace" : 344,
"surplusSpace" : "66",
"payGuide" : "停車費率:30 元/小時。停車時數未滿一小時者，以一小時計算。逾一小時者，其超過之不滿一小時部分，如不逾三十分鐘者，以半小時計算；如逾三十分鐘者，仍以一小時計算收費。",
"introduction" : "桃園市政府管轄之停車場",
"address" : "桃園區縣府路一號",
"wgsX" : 121.3011,
"wgsY" : 24.9934,
"parkId" : "P-TY-001"
}
        ]
        }
        **/

data class Parking(
    val parkingLots: List<ParkingLot>
)

data class ParkingLot(
    val address: String,
    val areaId: String,
    val areaName: String,
    val introduction: String,
    val parkId: String,
    val parkName: String,
    val payGuide: String,
    val surplusSpace: String,
    val totalSpace: Int,
    val wgsX: Double,
    val wgsY: Double
)