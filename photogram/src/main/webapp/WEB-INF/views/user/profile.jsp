<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<!--프로필 섹션-->
<section class="profile">
	<!--유저정보 컨테이너-->
	<div class="profileContainer">

		<!--유저이미지-->
		<div class="profile-left">
			<div class="profile-img-wrap story-border"
				onclick="popup('.modal-image')">
				<form id="userProfileImageForm">
					<input type="file" name="profileImageFile" style="display: none;"
						id="userProfileImageInput" />
				</form>

				<img class="profile-image" src="/upload/${dto.user.profileImageUrl}"
					onerror="this.src='/images/person.jpeg'" id="userProfileImage" />
			</div>
		</div>
		<!--유저이미지end-->

		<!--유저정보 및 사진등록 구독하기-->
		<div class="profile-right">
			<div class="name-group">
				<h2>${dto.user.name}</h2>

				<!-- c:when 과 c:choose 를 통해서, 조건문을 만들 수 있다. -->
				<c:choose>
					<c:when test="${dto.pageOwnerState}">
						<button class="cta" onclick="location.href='/image/upload'">사진등록</button>
					</c:when>

					<%-- 구독 여부 확인 --%>
					<c:otherwise>
						<c:choose>
							<c:when test="${dto.subscribeState}">
								<button class="cta blue" onclick="toggleSubscribe(${dto.user.id}, this)">구독취소</button>
							</c:when>
							<c:otherwise>
								<button class="cta" onclick="toggleSubscribe(${dto.user.id}, this)">구독하기</button>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>


				<button class="modi" onclick="popup('.modal-info')">
					<i class="fas fa-cog"></i>
				</button>
			</div>

			<div class="subscribe">
				<ul>
					<%-- <li><a href=""> 게시물<span>${dto.user.images.size()}</span> --%>
					<%-- 위에 것 대신에 UserProfileDto에 int 를 추가한뒤, UserService 에 userEntity 의 값을 가져온다. 그러면 아래처럼 된다. --%>
					<li><a href=""> 게시물<span>${dto.imageCount}</span>
					</a></li>
					<li><a href="javascript:subscribeInfoModalOpen(${dto.user.id});"> 구독정보<span>${dto.subscribeCount}</span>
					</a></li>
				</ul>
			</div>
			<div class="state">
				<h4>${dto.user.bio}</h4> s
				<h4>${dto.user.website}</h4>
			</div>
		</div>
		<!--유저정보 및 사진등록 구독하기-->
	</div>
</section>

<!--게시물컨섹션-->
<section id="tab-content">
	<!--게시물컨컨테이너-->
	<div class="profileContainer">
		<!--그냥 감싸는 div (지우면이미지커짐)-->
		<div id="tab-1-content" class="tab-content-item show">
			<!--게시물컨 그리드배열-->
			<div class="tab-1-content-inner">

				<!--아이템들-->
				<c:forEach var="image" items="${dto.user.images}"> <!-- JSTL 의 EL 표현식에서 변수명을 적으면 get 함수가 자동 호출된다. -->
				<div class="img-box">
					<a href=""> <img src="/upload/${image.postImageUrl}" /> <!-- upload 경로 폴더가 없으면 엑박이 뜨게 된다. -->
					<!-- WebMvcConfig 에 적은 내용을 보면, 이 Config 가 해당 주소를 낚아챈다. -->
					</a>
					<div class="comment">
						<!-- likeCount 는 없는 컬럼(생성한 컬럼, 별칭)이기 때문에 나오진 않음 -->
						<!-- UserService 에 dto 에 값을 넣어야한다. -->
						<a href="#" class=""> <i class="fas fa-heart"></i><span>${image.likeCount}</span>

						<%-- 아래의 방법으로도 좋아요 개수를 확인할 수 있다. (하지만, UserService 에 넣는 것이 낫다.)
							<!-- <a href="#" class=""> <i class="fas fa-heart"></i><span>${image.likes.size()}</span> -->
						--%>
						</a>
					</div>
				</div>
				</c:forEach>
				<!--아이템들end-->
			</div>
		</div>
	</div>
</section>

<!--로그아웃, 회원정보변경 모달-->
<div class="modal-info" onclick="modalInfo()">
	<div class="modal">
		<button onclick="location.href='/user/${dto.user.id}/update'">회원정보 변경</button>
		<button onclick="location.href='/logout'">로그아웃</button>
		<button onclick="closePopup('.modal-info')">취소</button>
	</div>
</div>
<!--로그아웃, 회원정보변경 모달 end-->

<!--프로필사진 바꾸기 모달-->
<div class="modal-image" onclick="modalImage()">
	<div class="modal">
		<p>프로필 사진 바꾸기</p>
		<button onclick="profileImageUpload(${dto.user.id}, ${principal.user.id})">사진 업로드</button>
		<button onclick="closePopup('.modal-image')">취소</button>
	</div>
</div>

<!--프로필사진 바꾸기 모달end-->

<div class="modal-subscribe">
	<div class="subscribe">
		<div class="subscribe-header">
			<span>구독정보</span>
			<button onclick="modalClose()">
				<i class="fas fa-times"></i>
			</button>
		</div>

		<div class="subscribe-list" id="subscribeModalList">



		</div>
	</div>

</div>


<script src="/js/profile.js"></script>

<%@ include file="../layout/footer.jsp"%>