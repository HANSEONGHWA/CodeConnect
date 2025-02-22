$(document).ready(function () {
    const pathname = window.location.pathname;
    const segments = pathname.split('/');
    const postId = segments[2];
    const apiUrl = `/api/posts/${postId}`;
    const postApiUrl = `/api/answers/${postId}`;

    // 상세페이지 조회
    $.ajax({
        url: apiUrl,
        method: "get",
        success: function (data) {
            console.log(data);
            $(".post-title").text(data.title);
            $(".data-type").text(data.type);
            $(".data-processType").text(data.processType);
            $(".data-size").text(data.size);
            $(".data-deadline").text(data.deadline);
            $(".data-contactMethod").text(data.contactMethod);
            $(".data-period").text(data.period);
            $(".data-position").html(data.position.map(pos => `<span>${pos}</span>`).join(' '));
            $(".data-techStack").html(data.techStack.map(tech => `<span>${tech}</span>`).join(' '));
            $(".post-description").text(data.description);
            $(".answer-list").html(data.answerList.map(answer =>
                `<li data-id="${answer.id}"><span class="comment-text">${answer.comment}</span><span class="button-container"><button class="modify-btn">수정</button>
                    <button class="delete-btn">삭제</button></span></li>`));
        },
        error: function (xhr, error) {
            console.error("댓글 등록", error);
            alert(xhr.responseText);
        }
    });


    //댓글 등록
    $(".btn").click(function () {
        const comment = $(".post-answer").val().trim();
        if (!comment) {
            alert("댓글을 입력하세요.");
            return;
        }

        $.ajax({
            url: postApiUrl,
            method: "post",
            contentType: "application/json",
            data: JSON.stringify({comment: comment}),
            success: function (response) {
                if (response && response.comment) {
                    $('.answer-list').prepend(`<li data-id="${response.id}"><span class="comment-text">${response.comment}</span> 
                    <span class="button-container"> <button class="modify-btn">수정</button>
                    <button class="delete-btn">삭제</button></span></li>`);
                    $(".post-answer").val((""));
                }
                alert("댓글이 등록되었습니다.");
            },
            error: function (xhr, status, error) {
                console.error("댓글 등록", error)
                console.error("서버 응답", xhr.responseText);

                alert("댓글을 등록할 수 없습니다. 다시 시도해주세요.");
            }
        });
    });


    // 댓글 수정
    $(document).on("click", ".modify-btn", function () {
        let $li = $(this).closest("li");
        let commentText = $li.find(".comment-text").text().trim();

        $li.data("original-comment", commentText);
        $li.html(`
        <textarea type="text" class="edit-input">${commentText}</textarea>
        <span class="button-container"><button class="save-btn">저장</button>
        <button class="cancel-btn">취소</button></span>`);
    });

    // 댓글 수정 저장
    $(document).on("click", ".save-btn", function () {
        let $li = $(this).closest("li");
        let commentId = $li.data("id");
        let newComment = $li.find(".edit-input").val().trim();

        if (!newComment) {
            alert("댓글을 입력하세요.");
            return;
        }

        $.ajax({
            url: `/api/answers/${commentId}`,
            method: "patch",
            contentType: "application/json",
            data: JSON.stringify({comment: newComment}),
            success: function (response) {
                $li.html(`
                <span class="comment-text">${response.comment}</span>
                <span class="button-container">
                    <button class="modify-btn">수정</button>
                    <button class="delete-btn">삭제</button>
                </span>`);

                alert("댓글을 수정했습니다.");
            },
            error: function (xhr, status, error) {
                console.error("댓글 수정", error);
                console.error("서버 응답:", xhr.responseText);

                alert("댓글을 수정할 수 없습니다. 다시 시도해주세요.");
            }
        });
    });

// 댓글 수정 취소
    $(document).on("click", ".cancel-btn", function () {
        let $li = $(this).closest("li");
        let originalComment = $li.data("original-comment");

        $li.html(`
        <span class="comment-text">${originalComment}</span>
        <span class="button-container">
            <button class="modify-btn">수정</button>
            <button class="delete-btn">삭제</button>
        </span>`);
    });

    //댓글 삭제
    $(document).on("click", ".delete-btn", function () {
        let $li = $(this).closest("li");
        let answerId = $li.data("id");
        if (!confirm("삭제하시겠습니까?"))
            return;

        $.ajax({
            url: `/api/answers/${answerId}`,
            method: "delete",
            success: function (response) {
                console.log(response);
                $li.remove();

                alert("댓글이 삭제되었습니다.")
            },
            error: function (xhr, status, error) {
                console.error("댓글 삭제", error);
                console.error("서버 응답:", xhr.responseText);

                alert("댓글을 삭제할 수 없습니다. 다시 시도해주세요.");
            }
        });
    });
});