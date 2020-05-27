function validateForm()
		{
								var job_location = document.getElementById("job_location").value;
								var edu = document.getElementById("edu").value;
								var job = document.getElementById("job").value;
								var jishu = document.getElementById("jishu").value.replace(/(^\s*)|(\s*$)/g, '');
								var yuyan = document.getElementById("yuyan").value.replace(/(^\s*)|(\s*$)/g, '');
								var salaryl = parseInt(document.getElementById("salaryl").value.replace(/(^\s*)|(\s*$)/g, ''));
								var salaryh = parseInt(document.getElementById("salaryh").value.replace(/(^\s*)|(\s*$)/g, ''));
								var exp = $("#exp").val();
								var industry = $("#industry").val();
								var people = $("#people").val();
								var financing = $("#financing").val();
								if(exp==null && industry==null && people==null && financing==null && job_location=="" && edu=="" && job=="" && jishu=="" && yuyan=="" && isNaN(salaryl) && isNaN(salaryh))
								{
									alert("最少要填一项喔！");
									return false;
								}else if( salaryl>salaryh){
									alert("薪资范围应是低-高的数值，请填写正确！");
									return false;
									//window.location.reload();
								}
								return true;
						}
						