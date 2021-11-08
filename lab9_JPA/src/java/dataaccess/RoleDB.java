
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import models.Role;


public class RoleDB {
      public Role get(Integer roleId) {
          EntityManager em = DBUtil.getEmFactory().createEntityManager();
          
          try {
              Role role = em.find(Role.class, roleId);
              return role;
          } finally {
              em.close();
          }
      }
}
