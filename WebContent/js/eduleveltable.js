
		    var m = new Map();
		    m.set("mba","MBA及以上");
		    m.set("zhongzhuan","中专/中技及以上");
		    m.set("chuzhong","初中");
		    m.set("boshi","博士");
		    m.set("boshihou","博士后");
		    m.set("buxian","学历不限");
		    m.set("benke","统招本科");
		    m.set("gaozhong","高中");
		    m.set("gaozhi","高职");
		    m.set("benke+","本科及以上");
		    m.set("zhongzhi","中职");
		    m.set("dazhuan","大专及以上");
		    m.set("shuoshi","硕士及以上");
			var edu = m.get(location.search.split("=")[1]);
			
			$("#title-4").text("您选择的职位学历要求: > "+edu);
			
    		/*************** 地区分布（company_location） 中国地图 **************/
    		var locationChart = echarts.init(document.getElementById('location'));
			var locationOpt = {  
			        //backgroundColor: 'rgba(255, 255, 255, 0.35)',
			        title: {  
			            text: edu+'-->公司地区分布',  
			            textStyle: {
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
			//点击省份跳转
			locationChart.on('click',function(params){
				var name = params.data.name;
				window.location.href="onEduDash.html?location="+name+"&param="+edu;
			});
			
			$.ajax({
				type : "post",
				async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
			    url : "eduleveltable",    //请求发送到TestServlet处
			    data : {"param":edu,"choice":"eduleveltable_location_hbase"},
			    dataType : "json",        //返回数据形式为json
			    success : function(idata) {
					data = idata.sort((a,b) => b.value - a.value);
			        locationChart.setOption({
				        //左侧小导航图标
				        visualMap: {
					        type: 'continuous',
					        x: 'left',  
					        y: 'center',
					        max:data[0].value,
					        text:['High','Low'],
					        textStyle:{
					        	color: '#fff'
					        },
					        realtime: false,
					        calculable : true,
					        color: ['orangered','yellow','lightskyblue']
				    	},
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
			                 data: data,
			             }]
			         });
			  	}
			});
			/********** 根据job_tag 对应的薪资情况  柱状图 ****************/
			var jobtagChart = echarts.init(document.getElementById("jobtag"));
			var jobtagOpt = {
					title: {  
			            text: edu+'职位索引标签(job_tag)-->薪资情况',  
			            textStyle: {
							color: '#fff',
							fontSize: 16,
						},  
			            x:'left' ,
			            subtext:"单位：K/月",
			            subtextStyle:{
			            	color: '#fff',
			            	fontSize: 12,
			            },
			        },
			        tooltip: { //鼠标定位显示
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
						//top: '1%',
						bottom: '1%',
						containLabel: true
					},
				    series: [{
				        data: [],
				        type: 'bar',
				        barWidth: 15,
				        itemStyle:{
							normal:{
								color:'#6495ED', //柱状体颜色
								barBorderRadius: [8,8,0,0],
							}
						}
				    }]
				};
			jobtagChart.setOption(jobtagOpt);
			
			$.ajax({
				type : "post",
				async : true,            
				url : "eduleveltable",  
				data : {"param":edu,"choice":"eduleveltable_jobtag_hbase"},
				dataType : "json",      
				success : function(idata) {
					jobtagChart.setOption({
						xAxis: {
					        data: idata.tag
					    },
						series: [{
							data : idata.amount
						    }]
					});
				}
			});
			
			/*************** 规模  (company_people)  雷达图 **************/
			var peopleChart = echarts.init(document.getElementById("people"));
			var peopleOpt = {
				    title: {
				        text: edu+'-->公司规模',
				        textStyle: {
							color: '#fff',
							fontSize: 16,
						},  
			            x:'left'
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
				        // shape: 'circle',
				        name: {
				            textStyle: {
				                color: '#fff',
				                //backgroundColor: '#999',
				                borderRadius: 2,
				                padding: [3, 5],
				    
				           }
				        },
				        nameGap: 2,//文字距离图形的距离
                        radius: 120,//设置雷达图大小
                        center: ['50%', '60%'],
				        indicator: [
					           { name: '5000-10000人',max:30000},
					           { name: '500-1000人',max:30000},
					           { name: '0-100人',max:30000},
					           { name: '10000以上',max:30000},
					           { name: '1000-5000人',max:30000},
					          
				        ],
				        splitLine : {
				            show : false,
				            lineStyle : {
				                color : 'rgba(131,141,158,5)', // 设置网格的颜色
				            },
				        },
				    },
				    series: [{				        
				        type: 'radar',
				        itemStyle: {normal: {areaStyle: {type: 'default'}}},
				        data : []
				    }]
				};
			peopleChart.setOption(peopleOpt);

			$.ajax({
				type : "post",
				async : true,            
				url : "eduleveltable",  
				data : {"param":edu,"choice":"eduleveltable_people_hbase"},
				dataType : "json",      
				success : function(idata) {
					peopleChart.setOption({
						series: [{
							data : idata
						    }]
					});
				}
			});
			
			/*************** 经验  (job_exp_require)  饼图 **************/
			var experienceChart = echarts.init(document.getElementById("experience"));
			var experienceOpt = {
				    title : {
				        text: edu+'-->经验要求',
				        textStyle: {
							color: '#fff',
							fontSize: 16,
						},  
			            x:'left'
				    },
				    tooltip : {
				        trigger: 'item',
				        formatter: "{a} <br/>{b} : {c} ({d}%)",
				    },
				    toolbox: {
				        show : true,
				        feature : {
				            dataView : {show: true, readOnly: false,},
				            saveAsImage : {show: true}
				        }
				    },
				    series : [
				        {
				            name: '',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
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
			experienceChart.setOption(experienceOpt);
			
			$.ajax({
				type : "post",
				async : true,            
				url : "eduleveltable",  
				data : {"param":edu,"choice":"eduleveltable_experience_hbase"},
				dataType : "json",      
				success : function(idata) {
					experienceChart.setOption({
						series: [{
							data : idata
						    }]
						});
				}
			});

			/*************** 上市情况（job_financing_stage） 柱状图 **************/
			var financingChart = echarts.init(document.getElementById("financing"));
			var financingOpt = {
					title: {  
			            text: edu+'-->融资情况',  
			            textStyle: {
							color: '#fff',
							fontSize: 16,
						},  
			            x:'left'  
			        },
			        tooltip: { //鼠标定位显示
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
				        splitLine:{show: false},//去除网格线
				        data: [],
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
						//top: '1%',
						bottom: '4%',
						containLabel: true
					},
				    series: [{
				        data: [],
				        type: 'bar',
				        barWidth: 15,
				        itemStyle:{
							normal:{
								color:'#43CD80', //柱状体颜色
								barBorderRadius: [8,8,0,0],
							}
						}
				    }]
				};
			financingChart.setOption(financingOpt);
			
			$.ajax({
				type : "post",
				async : true,            
				url : "eduleveltable",  
				data : {"param":edu,"choice":"eduleveltable_financing_hbase"},
				dataType : "json",      
				success : function(idata) {
					financingChart.setOption({
						xAxis: {
					        data: idata.financing
					    },
						series: [{
							data : idata.amount
						    }]
					});
				}
			});
		    var welfare = echarts.init($("#welfare")[0]);
		    var industry = echarts.init($("#industry")[0]);
			//industry
			$.ajax({
				type : "get",
				async : true,            
				url : "eduleveltable",  
				data : {"choice":"edulevelindustry","param":edu},
				dataType : "json",      
				success : function(idata) {
					 industry.setOption({  
						 title:{
								text:edu+"-->产业类型",
								textStyle: {
									color: '#fff',
									fontSize: 16,
								},
								x:"left",
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
								type: 'wordCloud',
							    shape: 'circle', //circle cardioid diamond triangle-forward triangle
							    width: '100%',
							    height: '100%',
							    gridSize: 10, //值越大，word间的距离越大，单位像素
							    sizeRange: [15, 37], //word的字体大小区间，单位像素
							    rotationRange: [-90, 90], //word的可旋转角度区间
							    textStyle: {
							    	normal: {
							            color: function() {
								    		var colors = ['#fda67e', '#81cacc', '#cca8ba', "#88cc81", "#82a0c5", '#fddb7e', '#735ba1', '#bda29a', '#6e7074', '#546570', '#c4ccd3'];
				                            return colors[parseInt(Math.random() * 5)];
							            } 
							        },
							        emphasis: {
							            shadowBlur: 0.5,
							            shadowColor: '#ffffff'
							        }
							    },
							    data: idata
							}]
					}); 
				}
			});
			//welfare		
			$.ajax({
				type : "get",
				async : true,            
				url : "eduleveltable",  
				data : {"choice":"edulevelwelfare","param":edu},
				dataType : "json",      
				success : function(idata) {
					datas = [];
					for (var i = 0; i < idata.length; i++) {
					    var item = idata[i];
					    datas.push({
					        name: item.value + '\n' + item.name,
					        value: [Math.random() * 100,Math.random() * 100,item.value],
					        label: {
					            normal: {
					                textStyle: {
					                    fontSize: 16,
					                    color:'#fff'
					                }
					            }
					        },
					        itemStyle: {
					            normal: {
					                color: 'rgba(104,184,55, 1)',
					            }
					        },
					    })
					}
					 welfare.setOption({
						 title:{
							text:edu+"-->公司福利",
							textStyle: {
								color: '#fff',
								fontSize: 16,
							},
						},
					    xAxis: [{
					        gridIndex: 0,
					        type: 'value',
					        show: false,
					        min: 0,
					        max: 100,
					        nameLocation: 'middle',
					        nameGap: 5
					    }],
					    yAxis: [{
					        gridIndex: 0,
					        min: 0,
					        show: false,
					        max: 100,
					        nameLocation: 'middle',
					        nameGap: 30
					    }],
							visualMap: [
						        {
						            left: 'right',
						            top: '10%',
						            dimension: 2,
						            min: 0,
						            show:false,
						            max: idata[0].value,
						            itemWidth: 30,
						            itemHeight: 120,
						            calculable: true,
						            precision: 0.1,
						            textGap: 30,
						            textStyle: {
						                color: '#fff'
						            },
						            inRange: {
						                symbolSize: [60,150]
						            },
						        }],
						        tooltip:{
						        	formatter: function (params, ticket) {
						            return params.name;
						        }},
						        toolbox: {
							        show : true,
							        feature : {
							            dataView : {show: true, readOnly: false,},
							            saveAsImage : {show: true}
							        }
							    },
							series: [{
								type: 'scatter',
						        symbol: 'circle',
						        symbolSize: 80,
						        label: {
						            normal: {
						                show: true,
						                formatter: '{b}',
						                color: '#fff',
						                textStyle: {
						                    fontSize: '16'
						                }
						            },
						        },
						        itemStyle: {
						            normal: {
						                borderWidth: '3',
						                borderType: 'solid',
						                borderColor: '#fff',
						                color: '#68b837',
						                shadowColor: '#68b837',
						                shadowBlur: 5
						            }
						        },
						    	data: datas
						    }]
						})
				}});
			
        	/********** 浏览器窗口改变时，重置报表大小 ****************/
			window.onresize = function() {
				location.resize();
				jobtag.resize();
				people.resize();
				experience.resize();	
				financing.resize();
				industry.resize();
				welfare.resize();
			} 