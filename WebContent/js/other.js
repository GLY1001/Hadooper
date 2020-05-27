	
    
    /*************** 各种公司规模招人的学历要求及人数  雷达图 **************/
	function reqPeople(){
	var reqPeopleChart = echarts.init(document.getElementById("main"));
	reqPeopleChart.clear();
	var reqPeopleOpt = {
		    title: {
		        text: '不同公司规模招聘的学历要求及人数',
		        textStyle: {
					color: '#fff',
				},  
	            x:'left'
		    },
		    //color: ['#8cbd89','#B22222','#c06f2a','#96b6d9','#2b5c91'],
		    legend: {
		        data: ["高中","博士","博士后","高职","统招本科","硕士及以上","本科及以上","学历不限","大专及以上","初中及以上","中职","中专/中技及以上","MBA及以上"],
		        orient: 'vertical',
		        left: 'left',
		        y: '100px',
		        itemWidth: 30,
		        itemHeight: 20,
		        textStyle:{
		        	color: '#fff',
		        	fontSize: 16, 
		        }
		    },
		    tooltip: {},
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false,},
		            saveAsImage : {show: true}
		        }
		    },
		    radar: {
		        name: {
		            textStyle: {
		                color: '#fff',
		           }, 
		        },
		        radius: 270,
		        center: ['50%', '55%'],
		        indicator: [
			           { name: '5000-10000人', max: 15000},
			           { name: '500-1000人', max: 30000},
			           { name: '0-100人', max: 15000},
			           { name: '10000以上', max: 10000},
			           { name: '1000-5000人', max: 10000},
		        ],
		        splitLine : {
		            show : false,
		            lineStyle : {
		                width : 1,
		                color : 'rgba(131,141,158,5)', // 设置网格的颜色
		            },
		        },
		    },
		    series: [{
		        type: 'radar',
		        data : [],
		        symbol:'circle',
		        symbolSize: 8,//拐点大小
		    }]
		};
	reqPeopleChart.setOption(reqPeopleOpt);

	$.ajax({
		type : "post",
		async : true,            
		url : "IndexServlet",  
		data : {"model":"four2"},
		dataType : "json",      
		success : function(idata) {
			reqPeopleChart.setOption({
				series: [{
					data : idata.reqPeople
				    }]
			});
		}
	});
	}
	reqPeople();
	
	/*************** 公司融资类型 **************/
	function welfare(){
	var welfareChar = echarts.init(document.getElementById("main"));
	welfareChar.clear();
	var welfareOpt = {
		title: {
			text: '公司融资类型',
			x: 'left',
			textStyle: {
				color: '#fff',
				
			}
		},
		tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    toolbox: {
	        show : true,
	        feature : {
	            dataView : {show: true, readOnly: false,},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    series : [
	    	{
	            name: '公司融资类型',
	            type: 'pie',
	            radius : '75%',
	            center: ['50%', '50%'],
	            data:[],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
	welfareChar.setOption(welfareOpt);
	$.ajax({
		type : "post",
		async : true,            
		url : "IndexServlet",  
		data : {"model":"four2"},
		dataType : "json",      
		success : function(idata) {
			welfareChar.setOption({        
				series: [{
					data : idata.financingPie
				    }]
				});
		}
	});		
	}

	/*************** 最火热的产业类型 **************/
	function industry(){
  	var industryChar = echarts.init(document.getElementById("main"));
		industryChar.clear();
	var industryOpt = {
			title:{
		        text: '产业类型分析',
		        x : 'left',
		        textStyle:{
		            color: '#fff'
		        }
		    },
		    tooltip: {
		        trigger: 'axis',
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false,},
		            saveAsImage : {show: true}
		        }
		    },
		    xAxis: {
		            type: 'category',
		            data: [],
		            axisPointer: {
		                type: 'shadow'
		            },
		            splitLine:{show: false},//去除网格线
					axisLine:{ //坐标字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				},
      				axisLabel: {
                        interval:0,
                        rotate:45
                     },
		        },
		    yAxis: {
		            type: 'value',
		            splitLine:{show: false},//去除网格线
					axisLine:{ //字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				},
      				min: 1000,
		        },
		    grid: {
				left: '6%',
				right: '6%',
				//top: '1%',
				bottom: '4%',
				containLabel: true
			},
		    series: [
		        {
		            type:'line',
	                
	                symbolSize: 12,//拐点大小
	                itemStyle : {
	                    normal : {
	                        lineStyle:{
	                            width:5,//折线宽度
	                            color:"#306491"//折线颜色
	                        }
	                    }
	                },
		        },
		    ]
	};
	industryChar.setOption(industryOpt);
	$.ajax({
		type : "post",
		async : true,            
		url : "IndexServlet",  
		data : {"model":"four2"},
		dataType : "json",      
		success : function(idata) {
			industryChar.setOption({        
				xAxis: {
					data: idata.index_industry.industry
				},
				series: [{
				    data: idata.index_industry.count
			    }]
			});
		}
	});
	}
	
	/*************** 各地区薪资水平对比 **************/
	function locationchart(){
  	var locationChart = echarts.init(document.getElementById("main"));;
  		locationChart.clear();
	var locationOpt = {
			title: {  
	            text: '各地区薪资水平对比',
	            textStyle: {
					color: '#fff',
					fontSize: 18,
				},
				subtext:"单位：k/月",
	            subtextStyle:{
	            	color: '#fff',
	            	fontSize: 16,
	            },
	            x:'center'  
	        },  
	        tooltip : {  
	            trigger: 'item',
	        },  
	        toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false,},
		            saveAsImage : {show: true}
		        }
		    },
	        //左侧小导航图标
	        visualMap: {
		        min: 5,
		        max: 22,
		        splitNumber: 5,
		        color: ['orangered','yellow','lightskyblue'],
		        textStyle: {
		            color: '#fff'
		        }
		    },
	        
	        //配置属性
	        series: [{
	            type: 'map',  
	            mapType: 'china',   
	            roam: true,  
	            label: {  
	                normal: {  
	                    show: true  //省份名称  
	                },  
	                emphasis: {  
	                    show: false  
	                }  
	            },  
	            data:[]
	        }]  
	    };  
		//使用制定的配置项和数据显示图表
	locationChart.setOption(locationOpt);
	$.ajax({
		type : "post",
		async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
	    url : "scalaryAnalysis",    //请求发送到TestServlet处
	    data : {},
	    dataType : "json",        //返回数据形式为json
	    success : function(idata) {
	        locationChart.setOption({        
	             series: [{
	            	 name:"地区分布",
	           	     type: 'map',  
	           	     mapType: 'china', 
	           	  	 roam: true,  
	                 label: {  
	                    normal: {  
	                        show: true  //省份名称  
	                    },  
	                    emphasis: {  
	                        show: false  
	                    }  
	              },  
	                 data: idata.location
	             },]
	         });
	  	}
	});
	}
	
	/*************** 各个职位索引标签的薪资对比 **************/
	function jobtag(){
  	var jobtagChart = echarts.init(document.getElementById("main"));
  		jobtagChart.clear();
	var jobtagOpt = {
			title: {  
	            text: '各职位索引标签的薪资水平对比',  
	            textStyle: {
					color: '#fff',
					fontSize: 18,
				},  
	            x:'left',
	            subtext:"单位：k/月",
	            subtextStyle:{
	            	color: '#fff',
	            	fontSize: 16,
	            },
	        },
	        tooltip: { //鼠标定位显示
				trigger: 'axis',
				axisPointer: {
					type: 'shadow'
				}},
				toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false,},
			            saveAsImage : {show: true}
			        }
			    },
		    xAxis: {
		        type: 'category',
		        data: [],
		        splitLine:{show: false},//去除网格线
		        axisLine:{ //坐标字体颜色
      				lineStyle:{
          				color:'#fff',
      				}
  				},
  				axisLabel: {
                    interval:0,
                    rotate:45
                 },
		    },
		    yAxis: {
		        type: 'value',
		        splitLine:{show: false},//去除网格线
		        axisLine:{ //坐标字体颜色
      				lineStyle:{
          				color:'#fff',
      				}
  				},
		    },
		    grid: {
				left: '6%',
				right: '6%',
				bottom: '4%',
				containLabel: true
			},
		    series: [{
		        data: [],
		        type: 'pictorialBar'
		    }]
		};
	jobtagChart.setOption(jobtagOpt);
	
	$.ajax({
		type : "post",
		async : true,            
		url : "scalaryAnalysis",  
		data : {},
		dataType : "json",      
		success : function(idata) {
			jobtagChart.setOption({
				xAxis: {
			        data: idata.jobtag.jobtag
			    },
				series: [{
					type:'bar',	
					data : idata.jobtag.salary,
					barWidth: 25,
					itemStyle:{
						normal:{
							color:'#2b5e91', //柱状体颜色
							label:{
								show: true, //开启显示
								position: 'top', //在右侧显示
								textStyle: { //数值样式
									color: '#fff',
								}
							}
						}
					}
					}
				]
			});
		}
	});
	}
	
	/*************** 各个学历平均薪资对比 **************/
	function edu(){
  	var eduChart = echarts.init(document.getElementById("main"));
  		eduChart.clear();
	var eduOpt = {
			title: {  
	            text: '各个学历平均薪资水平对比',  
	            textStyle: {
					color: '#fff',
					fontSize: 18,
				},  
	            x:'left',
	            subtext:"单位：k/月",
	            subtextStyle:{
	            	color: '#fff',
	            	fontSize: 16,
	            },
	        },
	        tooltip: { //鼠标定位显示
				trigger: 'axis',
				axisPointer: {
					type: 'shadow'
				}},
		    xAxis: {
		        type: 'category',
		        data: [],
		        splitLine:{show: false},//去除网格线
		        axisLine:{ //坐标字体颜色
      				lineStyle:{
          				color:'#fff',
      				}
  				},
  				axisLabel: {
                    interval:0,
                    rotate:45
                 },
		    },
		    yAxis: {
		        type: 'value',
		        splitLine:{show: false},//去除网格线
		        axisLine:{ //坐标字体颜色
      				lineStyle:{
          				color:'#fff',
      				}
  				},
		    },
		    toolbox: {
		        show : true,
		        feature : {
		            dataView : {show: true, readOnly: false,},
		            saveAsImage : {show: true}
		        }
		    },
		    grid: {
				left: '6%',
				right: '6%',
				bottom: '4%',
				containLabel: true
			},
		    series: [{
		        data: [],
		        type: 'pictorialBar'
		    }]
		};
	eduChart.setOption(eduOpt);
	
	$.ajax({
		type : "post",
		async : true,            
		url : "scalaryAnalysis",  
		data : {},
		dataType : "json",      
		success : function(idata) {
			eduChart.setOption({
				xAxis: {
			        data: idata.edulevel.edulevel
			    },
				series: [{
					symbol:"image://./img/coin.png",
					symbolRepeat:"fixed",
					symbolClip:true,
					symbolMargin:"3%",
					symbolSize:50,
					data : idata.edulevel.salary,
					z:10
				    },{
				    	type: 'pictorialBar',
			    	//设置图片透明
			    	itemStyle:{
			    		normal: {
			    			opacity:0.2
			    		}
			    	},
			        animationDuration: 0,
					symbol:"image://./img/coin.png",
					symbolRepeat:"fixed",
					symbolMargin:"3%",
					symbolSize:50,
					data:idata.edulevel.salary,
					z:5
			    }]
			});
		}
	});
	}
  