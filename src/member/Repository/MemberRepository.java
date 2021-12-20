package member.Repository;

import member.domain.Member;
import member.domain.MemberLevel;
import share.OracleData;
import share.StatementMaker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepository {
    OracleData dataSource = OracleData.getInstance();

    public void insert(Member member) {
        jdbcTemplate(con -> {
            String insertQuery = "insert into member values(HIBERNATE_SEQUENCE.NEXTVAL, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setString(1, member.getMemberLevel().toString());
            ps.setString(2, member.getName());
            ps.setString(3, member.getPassword());
            return ps;
        });
    }

    public Member selectById(Long memberId) {
        Member member = null;
        String selectQuery = "select * from member where member_id = ?";
        try (Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)){
            ps.setLong(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    member = memberMapper(rs);
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    private Member memberMapper(ResultSet rs) throws SQLException {
        Long memberId = rs.getLong(1);
        MemberLevel memberLevel = MemberLevel.valueOf(rs.getString(2));
        String memberName = rs.getString(3);
        String password = rs.getString(4);

        return new Member(memberId, memberName, password, memberLevel);
    }

    public Optional<Member> selectByName(String name) {
        Optional<Member> member = Optional.empty();
        String selectQuery = "select * from member where name = ?";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    member = Optional.of(memberMapper(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    public List<Member> selectAll() {
        List<Member> members = new ArrayList<>();
        Member member = null;
        String selectQuery = "select * from member";
        try(Connection con = dataSource.connect();
            PreparedStatement ps = con.prepareStatement(selectQuery)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    member = memberMapper(rs);
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    // password 변경
    public void update(Member member) {
        jdbcTemplate(con -> {
            String updateQuery = "update member set password = ? where member_id = ?";
            PreparedStatement ps = con.prepareStatement(updateQuery);
                ps.setString(1, member.getPassword());
                ps.setLong(2, member.getId());
            return ps;
        });
    }

    public void delete(Long memberId) {
        jdbcTemplate(con -> {
            String deleteQuery = "delete from member where member_id = ?";
            PreparedStatement ps = con.prepareStatement(deleteQuery);
            ps.setLong(1, memberId);
            return ps;
        });
    }

    private void jdbcTemplate(StatementMaker statementMaker) {
        try (Connection con = dataSource.connect();
             PreparedStatement ps = statementMaker.createPreparedStatement(con)) {
            ps.executeUpdate();
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}