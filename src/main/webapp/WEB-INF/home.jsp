<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container">
    <div class="jumbotron">
        <c:if test="${sessionScope.user != null}">
            <h1>Hello <c:out value="${sessionScope.user.firstName}"/></h1>
            <h1>Welcome to e-shoppers!
            </h1>
        </c:if>
    </div>
    <div class="row">
        <c:forEach var="product" items="${products}">
            <div class="col-sm-4">
                <div class="card h-100 mb-4">
                    <div class="card-body">
                        <h5 class="card-title">
                            <c:out value="${product.name}"/>
                        </h5>
                        <p class="card-body">
                            <c:out value="${product.description}"/>
                        </p>
                        <p class="card-text">
                            Price: $
                            <c:out value="${product.price}"/>
                        </p>
                        <a href="#" type="button" class="card-link btn btn-light ">
                            Add toCart
                        </a>
                    </div>
                </div>
            </div>

        </c:forEach>
    </div>
</div>

<%@include file="includes/footer.jsp" %>

