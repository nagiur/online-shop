<%-- signup.jsp--%>
<%@include file="includes/header.jsp" %>
<%@include file="includes/navigation.jsp" %>

<div class="container">
    <br/>

    <h2 class="h2">Sign Up</h2>
    <hr class="mb-4">

    <form class="form-horizontal" role="form" action="<c:url value="/signup"/> " method="post">
        <div class="form-group ">
            <label for="username">Username</label>
            <input type="text" class="form-control" id="username" value="${userDto.username}" name="username" placeholder="">

            <c:if test="${errors.username != null}">
                <small class="text-danger">${errors.username}</small>
            </c:if>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" value="${userDto.email}" name="email" placeholder="you@example.com">

            <c:if test="${errors.email != null}">
                <small class="text-danger">${errors.email}</small>
            </c:if>
        </div>

        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" name="password" placeholder="">

            <c:if test="${errors.password != null}">
                <small class="text-danger">${errors.password}</small>
            </c:if>
        </div>

        <div class="form-group">
            <label for="passwordConfirmed">Password Confirmed</label>
            <input type="password" class="form-control" id="passwordConfirmed" name="passwordConfirmed">

            <c:if test="${errors.passwordConfirmed != null}">
                <small class="text-danger">${errors.passwordConfirmed}</small>
            </c:if>
        </div>

        <div class="form-group">
            <label for="firstname">First Name</label>
            <input type="text" class="form-control" id="firstname" value="${userDto.firstName}" name="firstname" placeholder="">

            <c:if test="${errors.firstname != null}">
                <small class="text-danger">${errors.firstname}</small>
            </c:if>
        </div>

        <div class="form-group">
            <label for="lastname">Last Name</label>
            <input type="text" class="form-control" id="lastname" value="${userDto.lastName}" name="lastname" placeholder="">

            <c:if test="${errors.lastname != null}">
                <small class="text-danger">${errors.lastname}</small>
            </c:if>
        </div>


        <hr class="mb-4"/>
        <div class="form-group">
            <button type="submit" class="btn btn-primary btn-lg">Signup</button>
        </div>
    </form>

</div>

<%@include file="includes/footer.jsp" %>

