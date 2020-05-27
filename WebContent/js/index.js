/********** 搜索栏 ****************/
    
    //提示框的所有数据
    var test_list = ["人工智能","图像处理","图像算法","图像识别","数据分析","数据挖掘","机器学习","机器视觉","深度学习","算法","算法工程师","算法研究员"
    	,"自然语言处理","语音识别","MBA及以上","中专/中技及以上","初中及以上","博士","博士后","学历不限","统招本科","高中","高职","本科及以上","中职"
    	,"大专及以上","硕士及以上"];
    var old_value = "";
    var highlightindex = -1;   //高亮
    //弹出框的提示功能函数
    function AutoComplete(auto, search, mylist) {
        if ($("#" + search).val() != old_value || old_value == "") {
        	var autoNode = $("#" + auto);   //缓存对象（提示框）
            var carlist = new Array();
            var n = 0;
            old_value = $("#" + search).val();  //获取到输入框内容
            for (i in mylist) {  //遍历弹出框的所有数据，即test_list数组
                if (mylist[i].indexOf(old_value) >= 0) { //判断输入内容是否存在于test_list数组，>=0表示存在，则将mylist的内容加载到carlist数组
                    carlist[n++] = mylist[i];
                } 
            }
            if (carlist.length == 0) { //
                autoNode.hide();
                return;
            }
            autoNode.empty();  //清空上次的记录
            for (i in carlist) {
				/* 控制一下提示框显示数据的个数 */
				if(i>5){
					return;
				}
                var wordNode = carlist[i];   //弹出框里的每一条内容
                var newDivNode = $("<div>").attr("id", i);    //设置弹出框里的每一条内容的id值
                newDivNode.attr("style", "font:14px/25px arial;height:25px;padding:0 8px;cursor: pointer;"); //添加css效果
                newDivNode.html(wordNode).appendTo(autoNode);  //追加到弹出框
                //鼠标移入高亮，移开不高亮
                newDivNode.mouseover(function () {
                    if (highlightindex != -1) {        //原来高亮的节点要取消高亮（是-1就不需要了）
                        autoNode.children("div").eq(highlightindex).css("background-color", "white");
                    }
                    //记录新的高亮节点索引
                    highlightindex = $(this).attr("id");
                    $(this).css("background-color", "#ebebeb");
                });
                newDivNode.mouseout(function () {
                    $(this).css("background-color", "white");
                });
                //鼠标点击文字上屏
                newDivNode.click(function () {
                    //取出高亮节点的文本内容
                    var comText = autoNode.hide().children("div").eq(highlightindex).text();
                    highlightindex = -1;
                    //文本框中的内容变成高亮节点的内容
                    $("#" + search).val(comText);
                })
                if (carlist.length > 0) {    //如果返回值有内容就显示出来
                    autoNode.show();
                } else {               //服务器端无内容返回 那么隐藏弹出框
                    autoNode.hide();
                    //弹出框隐藏的同时，高亮节点索引值也变成-1
                    highlightindex = -1;
                }
            }
        }
    }
   
    $(function () {
        old_value = $("#search_text").val();
        $("#search_text").focus(function () { //当输入框获取到鼠标的焦点时
            if ($("#search_text").val() !== "") {
                AutoComplete("auto_div", "search_text", test_list);  //调用AutoComplete函数
            }
        });
        $("#search_text").keyup(function () { //当按钮被松开时，发生 keyup 事件
            AutoComplete("auto_div", "search_text", test_list);
        });
    });   
	/* 搜索的函数 ：点击搜索按钮时调用*/
	function searchfun(){
		var search_text = document.getElementById("search_text");
		//判断输入的搜索内容是否存在于标签云内
		var index = test_list.indexOf(search_text.value); //不存在返回-1
		var search_map = {"人工智能": "tag.html?param=ai","图像处理": "tag.html?param=gd","图像算法": "tag.html?param=ga",
				"图像识别": "tag.html?param=gs","数据分析": "tag.html?param=da","数据挖掘": "tag.html?param=dd",
				"机器学习": "tag.html?param=ml","机器视觉": "tag.html?param=mv","深度学习": "tag.html?param=dl",
				"算法": "tag.html?param=a","算法工程师": "tag.html?param=ae","算法研究员": "tag.html?param=ar",
				"自然语言处理": "tag.html?param=nd","语音识别": "tag.html?param=vs",
				"MBA及以上":"eduleveltable.html?param=mba","中专/中技及以上":"eduleveltable.html?param=zhongzhuan",
				"初中":"eduleveltable.html?param=chuzhong","博士":"eduleveltable.html?param=boshi","博士后":"eduleveltable.html?param=boshihou",
				"学历不限":"eduleveltable.html?param=buxian","统招本科":"eduleveltable.html?param=benke",
				"高中":"eduleveltable.html?param=gaozhong","高职":"eduleveltable.html?param=gaozhi","本科及以上":"eduleveltable.html?param=benke+",
				"中职":"eduleveltable.html?param=zhongzhi","大专及以上":"eduleveltable.html?param=dazhuan","硕士及以上":"eduleveltable.html?param=shuoshi"};	   
		if(index == -1){	
			alert("您输入的搜索内容不存在，请重新输入进行搜索！");
			window.location.reload();
		}else{
			window.location.href = search_map[search_text.value];
		}
	}

	/********** 标签云 ****************/
	window.onload = function() {
		try {//设置文本颜色和活动标记的轮廓
			TagCanvas.Start('myCanvas','tags',{
				textColour: '#fff',
    			outlineColour: '#0dc9a2',
    			reverse: true,
    			depth: 1,
    			maxSpeed: 0.05,
  			});
		} catch(e) {
  			//出错了，隐藏了画布容器
  			document.getElementById('myCanvasContainer').style.display = 'none';
		}
	 };
		 
		 //各职位所需掌握的前10个技能点 top10 降序
			var wordsegcharts = echarts.init($("#jobskill")[0]);
			wordsegcharts.setOption({
				title: {
					text: '各职位所需掌握的前10个技能点 top10 ',
					x: 'left',
					y: '5',
					textStyle: {
						color: '#fff',
						fontSize: 18,
					},
				tooltip: {},
				toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false,},
			            saveAsImage : {show: true}
			        }
			    },
				series:{
					type:"pie",
					radius : '55%',
					center: ['50%', '60%'],
					}
				}
			})
			//pie---jobtag
			function setBar(){
				var v = $("#cbar").val();
				$.ajax({
					type : "post",
					async : true,            
					url : "IndexServlet",  
					data : {"param":v,"model":"single10"},
					dataType : "json",      
					success : function(data) {
						console.log(v);
						console.log(data);
						wordsegcharts.setOption({
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
								radius : '55%',
								center: ['50%', '60%'],
						        data: data
						    }]
						});
					}
				});
		}
			/*************** 各个职位具体的技术要求 **************/
			var jobrequireChart= echarts.init(document.getElementById("jobrequire"));
			jobrequireOpt = {
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
			        symbolSize: 120,
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
			        data: []
			    }]
			};
			jobrequireChart.setOption(jobrequireOpt);
			
			function wordcloud(){
				var choice = $("#pie20").val();
				$.ajax({
					type : "post",
					async : true,
					url: "IndexServlet",
					data : {"param":choice,"model":"single"},
					dataType : "json",
					success: function (idata) {	
						datas = [];
						for (var i = 0; i < idata.length; i++) {
						    var item = idata[i];
						    datas.push({
						        name: item.value + '\n' + item.name,
						        value: [Math.random() * 100,Math.random() * 100,item.value],
						        label: {
						            normal: {
						                textStyle: {
						                    fontSize: 18,
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
						
						jobrequireChart.setOption({ 
							toolbox: {
						        show : true,
						        feature : {
						            dataView : {show: true, readOnly: false,},
						            saveAsImage : {show: true}
						        }
						    },visualMap:{max:idata[0].value},
					        tooltip:{
					        	formatter: function (params, ticket) {
					            return params.name;
					        }},
							series: [{
								type: 'scatter',
						        symbol: 'circle',
						        itemStyle: {
				                    normal: {
				                        opacity: 0.8
				                    }
				                },
						    	data: datas
						    }]
						});
					}
				});
			 };
		    
    	$(function() {
    		//各职位所需掌握的前10个技能点 top10 降序
  		  $.ajax({
			type : "post",
			async : true,            
			url : "IndexServlet",  
			data : {"param":"机器学习","model":"single10"},
			dataType : "json",      
			success : function(data) {
				wordsegcharts.setOption({
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
						radius : '55%',
						center: ['50%', '60%'],
				    	data: data,
				    }]
				});
			}
		});
  		  
  		//jobrequire
			$.ajax({
				type : "post",
				async : true,
				url: "IndexServlet",
				data : {"param":"人工智能","model":"single"},
				dataType : "json",
				success: function (idata) {
					datas = [];
					for (var i = 0; i < idata.length; i++) {
					    var item = idata[i];
					    datas.push({
					        name: item.value + '\n' + item.name,
					        value: [Math.random() *i*10*0.5,Math.random() * 100,item.value],
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
					jobrequireChart.setOption({
						title: {
							text: '各个职位具体的技术要求',
							textStyle: {
								color: '#fff',
								fontSize: 18,
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
					                symbolSize: [50,100]
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
					});
				}
			});
			
			/*************** 最新最热的技术（job_tag) top10 降序 **************/
			var jobtagChar = echarts.init(document.getElementById("jobtag"));
			var jobtagOpt = {
    			title: {
        			text: '最新最热的技术（job_tag) top10',
        			x: 'left',
					textStyle: {
						color: '#fff',
						fontSize: 18,
					}
    			},
				tooltip: { //鼠标定位显示
					trigger: 'axis',
					axisPointer: {
						type: 'shadow'
					}
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
					//top: '1%',
					bottom: '4%',
					containLabel: true
				},
				xAxis: {
					type: 'value',
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
					type: 'category',
					splitLine:{show: false},//去除网格线
					data: [],
					axisLine:{ //字体颜色
          				lineStyle:{
              				color:'#fff',       
          				}
      				},
					
				},
				series: [
					{
						name: '数量',
						type: 'bar',
						barWidth: 10,
						data: [],
						itemStyle:{
							normal:{
								barBorderRadius: [0,5,5,0],
								color:'#c33a37', //柱状体颜色
								label:{
									show: true, //开启显示
									position: 'right', //在右侧显示
									textStyle: { //数值样式
										color: '#fff',
									}
								}
							}
						}
					}
				]
			};
			jobtagChar.setOption(jobtagOpt);
			
			/*************** 最火热的职位 top20 降序 **************/
			//https://github.com/ecomfe/echarts-wordcloud
			//初始化echarts实例
			var jobnameChart= echarts.init(document.getElementById("jobname"));
			var jobnameOpt = {
				title:{
					text:"最火热的职位(jon_name) top20",
					textStyle: {
						color: '#fff',
						fontSize: 18,
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
				    gridSize: 1, //值越大，word间的距离越大，单位像素
				    sizeRange: [15, 35], //word的字体大小区间，单位像素
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
				    data: []
				}],
				//backgroundColor: 'rgba(255, 255, 255, 0.7)'
			};
			jobnameChart.setOption(jobnameOpt);
		
			/*************** 平均薪资前五的职位名称  top5 **************/
			var salary_avgChar = echarts.init(document.getElementById("salary_avg"));
			var salary_avgOpt = {
    			title: {
        			text: '平均薪资前五的职位名称 top5',
        			x: 'left',
					textStyle: {
						color: '#fff',
						fontSize: 18,
					},
					subtext:"单位：K/月",
		            subtextStyle:{
		            	color: '#fff',
		            	fontSize: 16,
		            },
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
    		    color: ['#4d7d4a','#B22222','#c06f2a','#96b6d9','#2b5c91'],
    		    series : [
       		        {
    		            name:'平均薪资前五的职位名称 top5',
    		            type:'pie',
    		            radius: ['50%', '60%'],
    		            avoidLabelOverlap: false,
    		            label: {
    		                normal: {
    		                    show: false,
    		                    position: 'center'
    		                },
    		                emphasis: {
    		                    show: true,
    		                    textStyle: {
    		                        fontSize: '30',
    		                        fontWeight: 'bold'
    		                    }
    		                }
    		            },
    		            labelLine: {
    		                normal: {
    		                    show: false
    		                }
    		            },
    		            data:[]
    		        }
    		    ]
			};
			salary_avgChar.setOption(salary_avgOpt);
			
			/*************** 薪资最高的职位 top10 降序 **************/
			var salaryOftagChar = echarts.init(document.getElementById("salaryOftag"));
			var salaryOftagOpt = {
    			title: {
        			text: '薪资最高的职位(job_tag) top10',
        			x: 'left',
					textStyle: {
						color: '#fff',
						fontSize: 18,
					},
					subtext:"单位：K/月",
		            subtextStyle:{
		            	color: '#fff',
		            	fontSize: 14,
		            },
    			},
				tooltip: { //鼠标定位显示
					trigger: 'axis',
					axisPointer: {
						type: 'shadow'
					}
				},
				toolbox: {
			        show : true,
			        feature : {
			            dataView : {show: true, readOnly: false,},
			            saveAsImage : {show: true}
			        }
			    },
				grid: {
					left: '0%',
					right: '0%',
					//top: '1%',
					bottom: '4%',
					containLabel: true
				},
				xAxis: {
					type: 'value',
					splitLine:{show: false},//去除网格线
					axisLine:{ //坐标字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				},
      				axisLabel: {
      					show: false,
      				}
					
				},
				yAxis: {
					type: 'category',
					splitLine:{show: false},//去除网格线
					data: [],
					axisLine:{ //字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				},
				},
				series: [
					{
						name: '数量',
						type: 'bar',
						data: [],
						barWidth: 10,
						itemStyle:{
							normal:{
								barBorderRadius: [0,5,5,0],
								color:'#3CB371', //柱状体颜色
								label:{
									show: true, //开启显示
									position: 'right', //在右侧显示
									textStyle: { //数值样式
										color: '#fff',
									}
								}
							}
						}
					}
				]
			};
			salaryOftagChar.setOption(salaryOftagOpt);
			
			
			/*************** 最火热的技术词的出现次数 top10 降序 **************/
			var pointwordChar = echarts.init(document.getElementById("pointword"));
			var pointwordOpt = {
    			title: {
        			text: '最火热的技术词的出现次数 top10',
        			x: 'left',
					textStyle: {
						color: '#fff',
						fontSize: 18,
					}
    			},
				tooltip: { //鼠标定位显示
					trigger: 'axis',
					axisPointer: {
						type: 'shadow'
					}
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
					//top: '1%',
					bottom: '4%',
					containLabel: true
				},
				xAxis: {
					type: 'value',
					splitLine:{show: false},//去除网格线
					axisLine:{ //坐标字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				},
				},
				yAxis: {
					type: 'category',
					data: [],
					splitLine:{show: false},//去除网格线
					axisLine:{ //字体颜色
          				lineStyle:{
              				color:'#fff',
          				}
      				},
					
				},
				series: [
					{
						name: '数量',
						type: 'bar',
						data: [],
						barWidth:10,
						itemStyle:{
							normal:{
								barBorderRadius: [0,5,5,0],
								color:'#43698e', //柱状体颜色
								label:{
									show: true, //开启显示
									position: 'right', //在右侧显示
									textStyle: { //数值样式
										color: '#fff',
									}
								}
							}
						}
					}
				]
			};
			pointwordChar.setOption(pointwordOpt);
			
			
				
			//异步
				$.ajax({
					type : "post",
					async : true,            
					url : "IndexServlet",  
					data : {"model":"four1"},
					dataType : "json",      
					success : function(idata) {
						salaryOftagChar.setOption({        
							yAxis: {
								data: idata.salaryparam.job_tag
							},
							series: [{
							name: '薪资',
						    data: idata.salaryparam.job_salary
						    }]
						});

						salary_avgChar.setOption({        
							series: [{
								data : idata.avg_salaryparam
							    }]
							});

						jobnameChart.setOption({        
							series: [{
						    data: idata.jobnameparam
						    }]
						});

						jobtagChar.setOption({        
							yAxis: {
								data: idata.jobtagparam.job_tag
							},
							series: [{
							name: '数量',
						    data: idata.jobtagparam.number
						    }]
						});
					}
				});
				
				setTimeout(function(){
					$.ajax({
						type : "post",
						async : true,            
						url : "IndexServlet",  
						data : {"model":"four2"},
						dataType : "json",      
						success : function(idata) {
					pointwordChar.setOption({        
						yAxis: {
							data: idata.pointwordparam.pointword
						},
						series: [{
						name: '数量',
					    data: idata.pointwordparam.number
					    }]
						});
					}
				});
		},500);
				
        	/********** 浏览器窗口改变时，重置报表大小 ****************/
			window.onresize = function() {
				jobtag.resize();
				jobname.resize();
				myCanvasContainer.resize();
				myCanvas.resize();
				salaryOftag.resize();
				salary_avg.resize();	
				jobskill.resize();
				pointword.resize();
				jobrequire.resize();
			}
		});