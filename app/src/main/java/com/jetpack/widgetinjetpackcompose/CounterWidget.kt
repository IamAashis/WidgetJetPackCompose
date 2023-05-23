package com.jetpack.widgetinjetpackcompose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

/**
 * Created by Aashis on 23,May,2023
 */
object CounterWidget : GlanceAppWidget() {

    val countKey = intPreferencesKey("count")

    @Composable
    override fun Content() {
        val count = currentState(key = countKey) ?: 0
        androidx.glance.layout.Column(
            modifier = GlanceModifier.background(Color.DarkGray).fillMaxSize(),
            verticalAlignment = androidx.glance.layout.Alignment.Vertical.CenterVertically,
            horizontalAlignment = androidx.glance.layout.Alignment.Horizontal.CenterHorizontally
        ) {
            Text(
                text = count.toString(), style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    color = ColorProvider(Color.White),
                    fontSize = 26.sp
                )
            )
            Button(text = "Click", onClick = actionRunCallback(IncrementActionCallBack::class.java))
        }
    }
}

class SimpleCounterWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = CounterWidget

}

class IncrementActionCallBack : ActionCallback {
    override suspend fun onAction(
        context: Context, glanceId: GlanceId, parameters: ActionParameters
    ) {
        updateAppWidgetState(context, glanceId) {
            val currentCount = it[CounterWidget.countKey]
            if (currentCount != null) {
                it[CounterWidget.countKey] = currentCount + 1
            } else {
                it[CounterWidget.countKey] = 1
            }
        }
        CounterWidget.update(context, glanceId)
    }

}