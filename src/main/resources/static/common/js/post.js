// 페이지 로드 초기화
$(document).ready(function () {
    $('#techStack').select2();
    let currentPage = 0;
    loadData(currentPage);

    $('.form-select').on('change', function () {
        loadData(currentPage);
    });

    // 이전 페이지 버튼 클릭 시
    $("#prev_button").click(function () {
        if (currentPage > 1) {
            currentPage--;
            dataList(currentPage);
        }
    });

    // 다음 페이지 버튼 클릭 시
    $("#next_button").click(function () {
        currentPage++;
        dataList(currentPage);
    });
});

// 상세페이지로 이동(post_detail.html)
$(document).on('click', '#table-body tr', function () {
    const postId = $(this).data('id');
    window.location.href = `/posts/${postId}`;
});

// 데이터 요청 및 페이지에 추가
function loadData(pageNum) {
    $.ajax({
        url: "api/posts",
        method: "GET",
        data: {
            page: pageNum,
            type: $("#type").val(),
            techStack: $("#techStack").val(),
            position: $("#position").val(),
        },
        contentType: 'application/json',
        success: function (data) {
            console.log("전송한 데이터:", {
                page: pageNum,
                type: $("#type").val(),
                techStack: $("#techStack").val(),
                position: $("#position").val(),
            });
            console.log("서버 응답 데이터:", data);


            // 데이터 테이블에(화면) 표시
            dataList(pageNum, data.content, data.totalElements, data.size);

            // 페이지네이션 컨트롤러 생성, 업데이트
            pagination(data.totalPages, pageNum);
        },
        error: function (error) {
            console.log(error);
            showError();

        }
    });
}

// 테이블 데이터 리스트
function dataList(pageNum, data, totalElements, size) {
    $('#table-body').empty();
    $.each(data, function (index, item) {
        const rowNumber = totalElements - (pageNum * size) - index;

        const tbody = `
                    <tr data-id="${item.id}">
                        <td>${rowNumber}</td>
                        <td>${item.type}</td>
                        <td>${item.title}</td>
                        <td><span>${item.techStack.map(tech => `<span class="circle">${tech}</span>`).join(' ')}</span></td>
                        <td><span>${item.position.map(position => `<span class="circle">${position}</span>`).join(' ')}</span></td>
                    </tr>
                `;
        $('#table-body').append(tbody);
    });

    // 상세페이지 이동
    $('.page_link').click(function () {
        let id = $(this).attr('data-id');
        window.location.href = '/detail/' + id;
    });
}

function showError() {
    $('#table-body').empty();
    const tbody = `
                <tr class="no-data">
                   <td colspan="5">조회 데이터가 없습니다.</td>
                </tr>`;
    $('#table-body').append(tbody);
}

// 페이지네이션
function pagination(totalPages, pageNum) {
    let currentPage = pageNum;
    let pagination = $('#pagination ul');
    let startPage = Math.max(0, currentPage - 2);
    let endPage = Math.min(totalPages - 1, startPage + 4);
    pagination.empty();

    pagination.append(
        '<li class="page-item ' + (currentPage === 0 ? 'disabled' : '') + '">' +
        '<a class="page-link" onclick="loadData(' + (currentPage - 1) + ')" id="prev_button">이전</a></li>'
    );

    for (let page = startPage; page <= endPage; page++) {
        pagination.append(
            '<li class="page-item ' + (page === currentPage ? 'active' : '') + '">' +
            '<a class="page-link" onclick="loadData(' + page + ')">' + (page + 1) + '</a></li>'
        );
    }

    pagination.append(
        '<li class="page-item ' + (currentPage === totalPages ? 'disabled' : '') + '">' +
        '<a class="page-link" onclick="loadData(' + (currentPage + 1) + ')" id="next_button">다음</a></li>'
    );
}


