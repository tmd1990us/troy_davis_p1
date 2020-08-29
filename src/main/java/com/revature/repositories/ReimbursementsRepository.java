package com.revature.repositories;

public class ReimbursementsRepository {
    private String baseQuery = "SELECT * FROM project_1.ers_reimbursements ";
    private String baseInsert = "INSERT INTO project_1.ers_reimbursements ";
    private String baseUpdate = "UPDATE project_1.ers_reimbursements ";

    public void addFile(){
        File file = new File("myimage.gif");
        FileInputStream fis = new FileInputStream(file);
        PreparedStatement ps = conn.prepareStatement("INSERT INTO images VALUES (?, ?)");
        ps.setString(1, file.getName());
        ps.setBinaryStream(2, fis, file.length());
        ps.executeUpdate();
        ps.close();
        fis.close();
    }

}
