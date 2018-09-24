var charts = [
'Politics', 'Economy', 'Environment', 'Financials'

];

for ( var i = 0; i < charts.length; i++ ) {
  var value = charts[i];
  var min = 20;
  var max = 1000;

  var chart = AmCharts.makeChart(value, {
      "type": "serial",
      "theme": "light",
      "marginRight": 40,
      "marginLeft": 40,
      "autoMarginOffset": 20,
      "mouseWheelZoomEnabled":true,
      "dataDateFormat": "YYYY-MM-DD",
      "valueAxes": [{
          "id": "v1",
          "axisAlpha": 0,
          "position": "left",
          "ignoreAxisWidth":true
      }],
      "balloon": {
          "borderThickness": 1,
          "shadowAlpha": 0
      },
      "graphs": [{
          "id": "g1",
          "balloon":{
            "drop":true,
            "adjustBorderColor":false,
            "color":"#ffffff"
          },
          "bullet": "round",
          "bulletBorderAlpha": 1,
          "bulletColor": "#FFFFFF",
          "bulletSize": 5,
          "hideBulletsCount": 50,
          "lineThickness": 2,
          "title": "red line",
          "useLineColorForBulletBorder": true,
          "valueField": "value",
          "balloonText": "<span style='font-size:18px;'>[[value]]</span>"
      }],
      "chartScrollbar": {
          "graph": "g1",
          "oppositeAxis":false,
          "offset":30,
          "scrollbarHeight": 80,
          "backgroundAlpha": 0,
          "selectedBackgroundAlpha": 0.1,
          "selectedBackgroundColor": "#888888",
          "graphFillAlpha": 0,
          "graphLineAlpha": 0.5,
          "selectedGraphFillAlpha": 0,
          "selectedGraphLineAlpha": 1,
          "autoGridCount":true,
          "color":"#AAAAAA"
      },
      "chartCursor": {
          "pan": true,
          "valueLineEnabled": true,
          "valueLineBalloonEnabled": true,
          "cursorAlpha":1,
          "cursorColor":"#258cbb",
          "limitToGraph":"g1",
          "valueLineAlpha":0.2,
          "valueZoomable":true
      },
      "valueScrollbar":{
        "oppositeAxis":false,
        "offset":50,
        "scrollbarHeight":10
      },
      "categoryField": "date",
      "categoryAxis": {
          "parseDates": true,
          "dashLength": 1,
          "minorGridEnabled": true
      },
      "export": {
          "enabled": true
      },
      "dataProvider": [{
          "date": "2018-09-01",
          "value": (Math.floor(Math.random() * (max - min + 1)) + min)
      }, {
          "date": "2018-09-02",
          "value": (Math.floor(Math.random() * (max - min + 1)) + min)
      }, {
          "date": "2018-09-03",
          "value": (Math.floor(Math.random() * (max - min + 1)) + min)
      }, {
          "date": "2018-09-04",
          "value": (Math.floor(Math.random() * (max - min + 1)) + min)
      }, {
          "date": "2018-09-05",
          "value": (Math.floor(Math.random() * (max - min + 1)) + min)
      }, {
          "date": "2018-09-06",
          "value": (Math.floor(Math.random() * (max - min + 1)) + min)
      }, {
          "date": "2018-09-07",
          "value": (Math.floor(Math.random() * (max - min + 1)) + min)
      }]
  });

  chart.addListener("rendered", zoomChart);

}



zoomChart();

function zoomChart() {
    chart.zoomToIndexes(chart.dataProvider.length - 40, chart.dataProvider.length - 1);
}