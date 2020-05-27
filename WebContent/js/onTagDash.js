$(function(){
	var url = window.location.href;
	var params = url.split("?")[1].split("&")
	var location = decodeURI(params[0].split("=")[1]);
	var param = decodeURI(params[1].split("=")[1]);
	var old = new Date().getTime();
	$("#site_a").text("> "+param+" >"+location);
	$.ajax({
		type : "post",
		async : true,
		url: "OnTagServlet",
		data : {"location":location,"model":"tag","param":param},
		dataType : "json",
		success: function (idata) {	
			console.log(idata);
			console.log((new Date().getTime())-old);
			var tag_location = echarts.init($("#tag_location")[0]);
			var tag_edu = echarts.init($("#tag_edu")[0]);
			var edu = echarts.init($("#edu")[0]);
			var tag = echarts.init($("#tag")[0]);
			
			tag_location.setOption({
				title:{
					text:location+"各职位薪资情况(k)",
					x:"center",
					textStyle:{
						color:"#fff",
						fontSize: 20,
					}
				},
				tooltip:{
					trigger: 'axis',
					
				},
				toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false,},
			            saveAsImage : {show: true}
			        }
			    },
			    grid: {
			    	left: '3%',
			        right: '4%',
			        containLabel: true
				},
			    xAxis:{
			    	type: 'category',
			    	data:idata.tag_salary.tag,
			    	splitLine:{show: false},//去除网格线
			    	axisLabel:{
			    		interval:0
			    	},
					axisLine:{ //坐标字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				},
      				axisLabel: {  
       				   interval:0,  
       				   rotate:40  
       				}, 
			    },
			    yAxis:{
			    	type: 'value',
			    	splitLine:{show: false},//去除网格线
					axisLine:{ //坐标字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				}
			    },
				series: [{
					type:"line",
			    	data: idata.tag_salary.salary,
			    	symbol:'circle',
					symbolSize: 10,
			    }]
			}); 
			
			tag_edu.setOption({
				title:{
					text:location+"-"+param+"各学历薪资概况(个)",
					x:"center",
					textStyle:{
						color:"#fff",
						fontSize: 20,
					}
				},
				tooltip:{
					trigger: 'axis',
					
				},
				toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false,},
			            saveAsImage : {show: true}
			        }
			    },
			    grid: {
			    	left: '3%',
			        right: '4%',
			        containLabel: true
				},
			    xAxis:{
			    	type: 'category',
			    	data:idata.edu_salary.edu,
			    	splitLine:{show: false},//去除网格线
			    	axisLabel:{
			    		interval:0
			    	},
					axisLine:{ //坐标字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				},
      				axisLabel: {  
       				   interval:0,  
       				   rotate:40  
       				}, 
			    },
			    yAxis:{
			    	type: 'value',
			    	splitLine:{show: false},//去除网格线
					axisLine:{ //坐标字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				}
			    },
				series: [{
					type:"line",
					symbol:'circle',
					symbolSize: 15,
					itemStyle : { 
						normal : { 
							color:'#8cd5c2', //改变折线点的颜色
							lineStyle:{ 
								color:'#8cd5c2' //改变折线颜色
							},
						}
					},
			    	data: idata.edu_salary.salary,
		    
			    }]
			});
			
			edu.setOption({
				title:{
					text:location+"-"+param+"各学历占比(个)",
					x:"center",
					textStyle:{
						color:"#fff",
						fontSize: 20,
					}
				},
				tooltip:{},
				toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false,},
			            saveAsImage : {show: true}
			        }
			    },
				series: [{
			    	data: idata.edu,
			    	type:"pie",
			        radius: '60%',
			        roseType: 'radius',
			    }]
			});
			//.sort(function (a, b) { return a.value - b.value; }),
			tag.setOption({
				title:{
					text:location+"职位占比(个)",
					x:"center",
					textStyle:{
						color:"#fff",
						fontSize: 20,
					}
				},
				tooltip:{},
				toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false,},
			            saveAsImage : {show: true}
			        }
			    },
				series: [{
					type:"pie",
			    	data: idata.tag,
			    	radius : '65%',
		            center: ['50%', '55%'],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
			    }]
			});
		}
	}); 
})