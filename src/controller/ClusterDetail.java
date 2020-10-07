package controller;

import bean.ClusterDetailBean;
import dao.ClusterDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ClusterDetail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int clusterId = Integer.parseInt(request.getParameter("clusterId"));
        try {
            List<ClusterDetailBean> list = ClusterDAO.getResultInDetail(clusterId);
            System.out.println("from Clusterdetail: " + list.size());
            request.setAttribute("detail", list);
            request.getRequestDispatcher("/quizHistoryDetail.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
