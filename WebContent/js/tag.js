	 
    
    var map ={"ai": "人工智能","gd": "图像处理","ga": "图像算法","gs": "图像识别","da": "数据分析"
    		,"dam": "数据分析师","dd": "数据挖掘","ml": "机器学习","mv": "机器视觉","dl": "深度学习"
    		,"a": "算法","ae": "算法工程师","ar": "算法研究员","nd": "自然语言处理","vs": "语音识别"};
    var tag = map[location.search.split("=")[1]];
    
    $("#title-4").text("您选择的职位索引标签: > "+tag);
    
    	$(function() {
		    var financing = echarts.init($("#financing")[0]);
		    var exp = echarts.init($("#exp")[0]);
		    var location = echarts.init($("#location")[0]);
		    var edu = echarts.init($("#edu")[0]);
		    var salary = echarts.init($("#salary")[0]);
		    var people = echarts.init($("#people")[0]);
		    var industry = echarts.init($("#industry")[0]); 
		    var welfare = echarts.init($("#welfare")[0]);
			//financing
			$.ajax({
				type : "get",
				async : true,            
				url : "Tag_view",  
				data : {"tag":tag,"param":"financing"},
				dataType : "json",      
				success : function(idata) {
					 financing.setOption({
						title:{
							text:tag+"-->公司融资情况",
							x:"left",
							textStyle:{
								color:"#fff",
								fontSize: 16,
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
					    	data: idata,
				            radius : [20, 95],
				            center: ['50%', '60%'],
				            roseType : 'radius',
					    }]
					}); 
				}
			}); 
			//people
			$.ajax({
				type : "get",
				async : true,            
				url : "Tag_view",  
				data : {"tag":tag,"param":"people"},
				dataType : "json",      
				success : function(idata) {
					 people.setOption({ 
						title:{
							text:tag+"-->公司规模",
							x:"left",
							textStyle:{
								color:"#fff",
								fontSize: 16,
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
					    	data: idata,
				            radius: ['50%', '70%'],
				            center: ['50%', '60%'],
				            label: {
				                normal: {
				                    show: false,
				                    position: 'center'
				                },
				                
				                emphasis: {
				                    show: true,
				                    textStyle: {
				                        fontSize: '20',
				                        fontWeight: 'bold'
				                    }
				                }
				            },
					    }]
					}); 
				}
			});
			//edu
			$.ajax({
				type : "get",
				async : true,            
				url : "Tag_view",  
				data : {"tag":tag,"param":"edu"},
				dataType : "json",      
				success : function(idata) {
					 edu.setOption({
						title:{
							text:tag+"-->学历要求",
							x:"left",
							textStyle:{
								color:"#fff",
								fontSize: 16,
							}
						},
						yAxis:{
							splitLine:{show: false},//去除网格线
					        axisLine:{ //坐标字体颜色
		          				lineStyle:{
		              				color:'#fff',
		          				}
		      				},
						},      
						xAxis: {
							type:"category",
							data: idata.edu,
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
						tooltip:{trigger:"axis"},
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
							top:"20%",
							bottom: 0,
							containLabel: true
						},
						series: [{
							type:"bar",
							barwidth: 15,
							itemStyle:{
								normal:{
									color:'#6495ED', //柱状体颜色
									//barBorderRadius: 8,
								}
							},
					    	data: idata.count,
					    }]
					}); 
				}
			});
			//exp
			$.ajax({
				type : "get",
				async : true,            
				url : "Tag_view",  
				data : {"tag":tag,"param":"exp"},
				dataType : "json",      
				success : function(idata) {
					 exp.setOption({  
						title:{
							text:tag+"-->经验要求",
							x:"left",
							textStyle:{
								color:"#fff",
								fontSize: 16,
							}
						},
						yAxis:{
							splitLine:{show: false},//去除网格线
					        axisLine:{ //坐标字体颜色
		          				lineStyle:{
		              				color:'#fff',
		          				}
		      				},
						},      
						xAxis: {
							type:"category",
							data: idata.exp,
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
						tooltip:{trigger:"axis"},
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
							type:"bar",
							barWidth: 15,
							itemStyle:{
								normal:{
									color:'#43CD80', //柱状体颜色
									//barBorderRadius: 8,
								}
							},
					    	data: idata.count
					    }]
					}); 
				}
			});
			//location
			$.ajax({
				type : "get",
				async : true,            
				url : "Tag_view",  
				data : {"tag":tag,"param":"location"},
				dataType : "json",      
				success : function(idata) {
					data = idata.sort((a,b) => b.value - a.value);
					 location.setOption({
						 title:{
							 text:tag+"-->公司地区分布",
							 x:"center",
							 textStyle:{
								 color:"#fff",
								 fontSize: 16,
							 }
						 },
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
						tooltip:{},
						toolbox: {
					        show : true,
					        feature : {
					            dataView : {show: true, readOnly: false,},
					            saveAsImage : {show: true}
					        }
					    },
						series: [{
							type: 'map',  
							name:"地区分布",
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
			//salary
			$.ajax({
				type : "get",
				async : true,            
				url : "Tag_view",  
				data : {"tag":tag,"param":"salary"},
				dataType : "json",      
				success : function(idata) {
					 salary.setOption({  
						title: {  
							text: tag+'-->薪资情况',  
					        textStyle: {
								color: '#fff',
								fontSize: 16,
							},  
							x:"center",
					        subtext:"单位：K/月",
				            subtextStyle:{
				            	color: '#fff',
				            	fontSize: 14,
				            },
					    },
						yAxis:{
							splitLine:{show: false},//去除网格线
					        axisLine:{ //坐标字体颜色
		          				lineStyle:{
		              				color:'#fff',
		          				}
		      				},
						},      
						xAxis: {
							type:"category",
							data: idata.salary,
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
						tooltip:{trigger:"axis"},
						toolbox: {
					        show : true,
					        feature : {
					            dataView : {show: true, readOnly: false,},
					            saveAsImage : {show: true}
					        }
					    },
						grid: {
							left: '4%',
							right: '4%',
							top:"20%",
							bottom: 0,
							containLabel: true
						},
						series: [{
							type:"bar",
							barWidth: 15,
					    	data: idata.count,
					    	itemStyle: {
					    		normal: {
					    			//barBorderRadius: 8,
					    		}
					    	}
					    }]
					}); 
				}
			});
			//industry
			$.ajax({
				type : "get",
				async : true,            
				url : "Tag_view",  
				data : {"tag":tag,"param":"industry"},
				dataType : "json",      
				success : function(idata) {
					 industry.setOption({  
						 title:{
								text:tag+"-->产业类型",
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
				url : "Tag_view",  
				data : {"tag":tag,"param":"welfare"},
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
								text:tag+"-->公司福利",
								textStyle: {
									color: '#fff',
									fontSize: 16,
								},
							},
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
				financing.resize();
				exp.resize();
				location.resize();
				edu.resize();	
				people.resize();
				salary.resize();
				industry.resize();
				welfare.resize();
			} 
			
			//点击省份跳转
			location.on('click',function(params){
				var name = params.data.name;
				window.location.href="onTagDash.html?location="+name+"&param="+tag;
			});

		}); 
	