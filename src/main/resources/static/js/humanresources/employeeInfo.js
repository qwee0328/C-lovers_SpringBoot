$(document).ready(function () {
	
	 // 검색 창 클릭시 div border변경
    $(".em_search").on("click", function () {
       $(".searchBox").css("border","1px solid #75B47D");
    });

	
	 // 아코디언 메뉴
    $(document).on("click",".Toggle", function () {

        if ($(this).attr("toggleView") == "true") {
            $(this).attr("toggleView", "false");

            $(this).parent().siblings(".Inner").css("display", "none");
            $(this).parent().css("background-color", "transparent");
            $(this).html(`<i class="fa-solid fa-plus"></i>`);

        } else {
            $(this).attr("toggleView", "true");

            $(this).parent().siblings(".Inner").css("display", "block");
            $(this).parent().css("background-color", "#f3f7f1");
            $(".Inner").css("background-color", "#f3f7f1");

            $(this).html(`<i class="fa-solid fa-minus"></i>`);

        }
    });
	
	
	// 왼쪽 부서 메뉴
	$.ajax({
		url:"/office/selectAllTaskNameEmpo"
	}).done(function(resp){
		let sum=0; // 총인원
		let dept = new Set(); //set으로 안겹치게 부서명
		
		
		for(let i=0;i<resp.length;i++){
			dept.add(resp[i].dept_name);
		}
		
		let deptArr = Array.from(dept); // set을 배열로
		
		let innerBox__title = $("<div class='innerBox__title cloverEmployee'>");
			let innerBox__title_div = $("<div class='searchName'>");
				innerBox__title_div.html(resp[0].office_name);
			innerBox__title.append(innerBox__title_div)	;
					let title_div = $("<div class='count'>");
					for(let i=0;i<resp.length;i++){
						sum+=resp[i].count;
					}
						title_div.html("("+sum+")");
					innerBox__title.append(title_div);
			
			let innerBox = $("<div class='innerBox'>");
		
		for(let i=0;i<deptArr.length;i++){
			let dept_sum =0;	
			let Box = $("<div class='Box'>");
					let Box_1 = $("<div class='Title Box__title' >");
						let naviIcon = $("<div class='naviIcon Toggle' toggleView='false'>");
							let i_con = $("<i class='fa-solid fa-plus'>");
						naviIcon.append(i_con);
						
						
						let Box_title = $("<div class='Title'>");
							let Box_title_div = $("<div class='searchName'>");
								Box_title_div.append(deptArr[i]);
						Box_title.append(Box_title_div);	
					let div = $("<div class='count'>");
					
					for(let k=0;k<resp.length;k++){
						
						if(deptArr[i]==resp[k].dept_name){
							dept_sum+=resp[k].count;
						}
						
					}
								div.html("("+dept_sum+")");
							Box_title.append(div);	
					Box_1.append(naviIcon);
					Box_1.append(Box_title);
				let contentBox = $("<div class='ContentBox Inner'>");
			
			for(let j=0;j<resp.length;j++){
				if(deptArr[i] == resp[j].dept_name){
					
						let content = $("<div class='Content'>");
							let content__div = $("<div class='searchName'>");
								content__div.append(resp[j].task_name);
							content.html(content__div);
							
							let content_div = $("<div class='count'>");
								content_div.append("("+resp[j].count+")");
								
							content.append(content_div);
							
						contentBox.append(content);
						
					Box.append(Box_1).append(contentBox);
				
				}
				innerBox.append(Box);
			}
			
			$(".left__innerBox").append(innerBox__title).append(innerBox);
			
		}
	})
	
	// 로딩되자마자 클로버 산업 누르면 직원 전체 불러오기
	selectAllEmp();
	
	// 클로버 산업 누르면 직원 전체 불러오기
	$(document).on("click",".cloverEmployee",function(){
		selectAllEmp();
	});
	
	// 오른쪽 직원 메뉴 - 전체 불러오기 함수
	function selectAllEmp(){
		$.ajax({
			url:"/office/selectDeptEmpo"
		}).done(function(resp){

			let dept = new Set(); //set으로 안겹치게 부서명
		
			for(let i=0;i<resp.length;i++){
				dept.add(resp[i].dept_name);
			}
			
			let deptArr = Array.from(dept); // set을 배열로
			
			for(let i=0;i<deptArr.length;i++){
				$(".members__Title").remove();
				$(".member__unitBox").remove();
				
				let members__Title = $("<div class='members__Title'>");
					let h4__Title = $("<h4>");
						h4__Title.append(resp[0].office_name);
					members__Title.append(h4__Title);
					
				let members__profile = $("<div class='members__profile'>");
				
				
				for(let i=0;i<resp.length;i++){
					
					 let memberBox = $("<div class='member__unitBox'>");
						let memberPhoto = $("<div class='memberPhoto'>");
						
							let isImg;
							if(resp[i].profile_img == "" || resp[i].profile_img == null){
									isImg = "/assets/profile.png";
							}else{
								isImg = "/uploads/"+resp[i].profile_img;
							}
						
							let img = $("<img src="+isImg+" alt=''>");
						memberPhoto.append(img);
						
						let memberText = $("<div class='memberText'>");
							let memberName = $("<div class='member__name'>");
								memberName.html(resp[i].name);
							let memberdepart = $("<div class='member__depart'>");
								memberdepart.html(resp[i].dept_name);
							let membergrade = $("<div class='member__grade'>")
								membergrade.html(resp[i].job_name);
						memberText.append(memberName).append(memberdepart).append(membergrade);
					memberBox.append(memberPhoto).append(memberText);
					
					members__profile.append(memberBox);
					
				}	
				
				$(".org__members").append(members__Title).append(members__profile);
			}
			
		})
	}
	
	
	// 부서 클릭하면
	$(document).on("click",".Box__title",function(){
		$.ajax({
			url:"/office/selectByDeptName",
			data: {
				dept_name : $(this).find(".searchName").text()
			}
		}).done(function(resp){
			$(".members__Title").remove();
			$(".member__unitBox").remove();
			
			let members__Title = $("<div class='members__Title'>");
				let h4__Title = $("<h4 style='color:#B5B5BB'>");
					h4__Title.append(resp[0].office_name);
					let Title__icon = $("<div class='officeArrow'><i class='fa-solid fa-angle-right'></div>");
					
					let Title_deptName = $("<h4>");
						Title_deptName.append(resp[0].dept_name);
					
				members__Title.append(h4__Title).append(Title__icon).append(Title_deptName);
				
			let members__profile = $("<div class='members__profile'>");
			
			
			for(let i=0;i<resp.length;i++){
				
				 let memberBox = $("<div class='member__unitBox'>");
					let memberPhoto = $("<div class='memberPhoto'>");
					
						let isImg;
						if(resp[i].profile_img == "" || resp[i].profile_img == null){
								isImg = "/assets/profile.png";
						}else{
							isImg = "/uploads/"+resp[i].profile_img;
						}
						
						let img = $("<img src="+isImg+" alt=''>");
					memberPhoto.append(img);
					
					let memberText = $("<div class='memberText'>");
						let memberName = $("<div class='member__name'>");
							memberName.html(resp[i].name);
						let memberdepart = $("<div class='member__depart'>");
							memberdepart.html(resp[i].dept_name);
						let membergrade = $("<div class='member__grade'>")
							membergrade.html(resp[i].job_name);
					memberText.append(memberName).append(memberdepart).append(membergrade);
				memberBox.append(memberPhoto).append(memberText);
				
				members__profile.append(memberBox);
				
			}	
			
			$(".org__members").append(members__Title).append(members__profile);
		});
		
	})
	

   // 팀명 클릭하면
   $(document).on("click",".Content",function(){
	   $.ajax({
		   url:"/office/selectByTaskName",
		   data:{
			   task_name: $(this).find(".searchName").text()
		   }
	   }).done(function(resp){
		   $(".members__Title").remove();
			$(".member__unitBox").remove();
			
			let members__Title = $("<div class='members__Title'>");
				let h4__Title = $("<h4 style='color:#B5B5BB'>");
					h4__Title.append(resp[0].office_name);
					let Title__icon = $("<div class='officeArrow' style='color:#B5B5BB'><i class='fa-solid fa-angle-right'></div>");
					
					let Title_deptName = $("<h4 style='color:#B5B5BB'>");
						Title_deptName.append(resp[0].dept_name);
					
					let Title__icon2 = $("<div class='officeArrow'><i class='fa-solid fa-angle-right'></div>");
					
					let Title_taskName = $("<h4>");
						Title_taskName.append(resp[0].task_name);
					
				members__Title.append(h4__Title).append(Title__icon).append(Title_deptName).append(Title__icon2).append(Title_taskName);
				
			let members__profile = $("<div class='members__profile'>");
			
			
			for(let i=0;i<resp.length;i++){
				
				 let memberBox = $("<div class='member__unitBox'>");
					let memberPhoto = $("<div class='memberPhoto'>");
					
						let isImg;
						if(resp[i].profile_img == "" || resp[i].profile_img == null){
								isImg = "/assets/profile.png";
						}else{
							isImg = "/uploads/"+resp[i].profile_img;
						}
					
						let img = $("<img src="+isImg+" alt=''>");
					memberPhoto.append(img);
					
					let memberText = $("<div class='memberText'>");
						let memberName = $("<div class='member__name'>");
							memberName.html(resp[i].name);
						let memberdepart = $("<div class='member__depart'>");
							memberdepart.html(resp[i].dept_name);
						let membergrade = $("<div class='member__grade'>")
							membergrade.html(resp[i].job_name);
					memberText.append(memberName).append(memberdepart).append(membergrade);
				memberBox.append(memberPhoto).append(memberText);
				
				members__profile.append(memberBox);
			}	
			
			$(".org__members").append(members__Title).append(members__profile);
	   })
   })

	// 검색창
	$(document).on("keyup",".em_search",function(){
		$.ajax({
			url:"/office/searchByName",
			data:{
				name:$(this).val()
			}
		}).done(function(resp){
			$(".members__Title").remove();
			$(".member__unitBox").remove();
			$(".members__profile").remove();
			
			let members__Title = $("<div class='members__Title'>");
				let h4__Title = $("<h4 style='color:#B5B5BB'>");
					h4__Title.append(resp[0].office_name);
					let Title__icon = $("<div class='officeArrow' style='color:#B5B5BB'><i class='fa-solid fa-angle-right'></div>");
					
					let Title_deptName = $("<h4 style='color:#B5B5BB'>");
						Title_deptName.append(resp[0].dept_name);
					
					let Title__icon2 = $("<div class='officeArrow'><i class='fa-solid fa-angle-right'></div>");
					
					let Title_taskName = $("<h4>");
						Title_taskName.append(resp[0].task_name);
					
				members__Title.append(h4__Title).append(Title__icon).append(Title_deptName).append(Title__icon2).append(Title_taskName);
				
			let members__profile = $("<div class='members__profile'>");
			
			
			for(let i=0;i<resp.length;i++){
				
				 let memberBox = $("<div class='member__unitBox'>");
					let memberPhoto = $("<div class='memberPhoto'>");
					
						let isImg;
						if(resp[i].profile_img == "" || resp[i].profile_img == null){
								isImg = "/assets/profile.png";
							}else{
								isImg = "/uploads/"+resp[i].profile_img;
							}
					
						let img = $("<img src="+isImg+" alt=''>");
					memberPhoto.append(img);
					
					let memberText = $("<div class='memberText'>");
						let memberName = $("<div class='member__name'>");
							memberName.html(resp[i].name);
						let memberdepart = $("<div class='member__depart'>");
							memberdepart.html(resp[i].dept_name);
						let membergrade = $("<div class='member__grade'>")
							membergrade.html(resp[i].job_name);
					memberText.append(memberName).append(memberdepart).append(membergrade);
				memberBox.append(memberPhoto).append(memberText);
				
				members__profile.append(memberBox);
				
			}	
			
			$(".org__members").append(members__Title).append(members__profile);
		})
	})


})

