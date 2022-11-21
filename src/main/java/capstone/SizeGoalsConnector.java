/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone;

import java.sql.SQLException;

/**
 *
 * @author Simranjit
 */
public class SizeGoalsConnector extends dataConnector {

    public void userSizeGoals(String neckTarget, String armsTarget, String waistTarget, String hipsTarget,
            String legsTarget, String neckInitial, String armsInitial, String waistInitial, String hipsInitial,
            String legsInitial) {
        try {
            String sql = "INSERT INTO SizeGoals(Neck_Target, Arms_Target, Waist_Target,"
                    + " Hips_Target, Legs_Target, Neck_Initial, Arms_Initial,"
                    + "Waist_Initial,Hips_Initial, Legs_Initial) VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, neckTarget);
            preparedStatement.setString(2, armsTarget);
            preparedStatement.setString(3, waistTarget);
            preparedStatement.setString(4, hipsTarget);
            preparedStatement.setString(5, legsTarget);
            preparedStatement.setString(6, neckInitial);
            preparedStatement.setString(7, armsInitial);
            preparedStatement.setString(8, waistInitial);
            preparedStatement.setString(9, hipsInitial);
            preparedStatement.setString(10, legsInitial);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row inserted into Size Goals Table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNeckTarget(int id, String neckTarget) {
        try {
            String sql = "UPDATE SizeGoals SET Neck_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, neckTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateArmsTarget(int id, String armsTarget) {
        try {
            String sql = "UPDATE SizeGoals SET Arms_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, armsTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateWaistTarget(int id, String waistTarget) {
        try {
            String sql = "UPDATE SizeGoals SET Waist_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, waistTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateHipsTarget(int id, String hipsTarget) {
        try {
            String sql = "UPDATE SizeGoals SET Hips_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, hipsTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateLegsTarget(int id, String legsTarget) {
        try {
            String sql = "UPDATE SizeGoals SET Legs_Target=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, legsTarget);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateNeckInitial(int id, String neckInitial) {
        try {
            String sql = "UPDATE SizeGoals SET Neck_Initial=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, neckInitial);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateArmsInitial(int id, String armsInitial) {
        try {
            String sql = "UPDATE SizeGoals SET Arms_Initial=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, armsInitial);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateWaistInitial(int id, String waistInitial) {
        try {
            String sql = "UPDATE SizeGoals SET Waist_Initial=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, waistInitial);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateHipsInitial(int id, String hipsInitial) {
        try {
            String sql = "UPDATE SizeGoals SET Hips_Initial=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, hipsInitial);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }

    public void updateLegsInitial(int id, String legsInitial) {
        try {
            String sql = "UPDATE SizeGoals SET Legs_Initial=? WHERE id=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, legsInitial);
            preparedStatement.setInt(2, id);
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                System.out.println("Row updated");
            }
        } catch (SQLException e) {
        }
    }
}
