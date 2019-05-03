package com.kimi.shop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_bus.*
import kotlinx.android.synthetic.main.row_bus.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.info
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class BusActivity : AppCompatActivity(), AnkoLogger {

    var busInfo: BusInfo? = null

    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://data.tycg.gov.tw/opendata/datalist/datasetMeta/")
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus)


        doAsync {

            val busService = retrofit.create(BusService::class.java)
            busInfo = busService.getBusInfo().execute().body()

            info { busInfo?.datas?.size }
            uiThread {
                recycler.layoutManager = LinearLayoutManager(this@BusActivity)
                recycler.setHasFixedSize(true)
                recycler.adapter = BusAdapter()


            }

        }

    }

    inner class BusAdapter() : RecyclerView.Adapter<BusViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.row_bus, parent, false)
            return BusViewHolder(view)
        }

        override fun getItemCount(): Int {
            return busInfo?.datas?.size ?: 0
        }

        override fun onBindViewHolder(holder: BusViewHolder, position: Int) {
            holder.bindView(busInfo?.datas!!.get(position))
        }

    }

    inner class BusViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val busId: TextView = view.bus_id
        val rountId: TextView = view.rount_id
        val speed: TextView = view.speed

        fun bindView(data: Data) {
            busId.text = data.BusID
            rountId.text = data.RouteID
            speed.text = data.Speed
        }

    }

}


data class BusInfo(
    val datas: List<Data>
)

data class Data(
    val Azimuth: String,
    val BusID: String,
    val BusStatus: String,
    val DataTime: String,
    val DutyStatus: String,
    val GoBack: String,
    val Latitude: String,
    val Longitude: String,
    val ProviderID: String,
    val RouteID: String,
    val Speed: String,
    val ledstate: String,
    val sections: String
)

interface BusService{
    @GET("download?id=b3abedf0-aeae-4523-a804-6e807cbad589&rid=bf55b21a-2b7c-4ede-8048-f75420344aed")
    fun getBusInfo(): Call<BusInfo>
}
