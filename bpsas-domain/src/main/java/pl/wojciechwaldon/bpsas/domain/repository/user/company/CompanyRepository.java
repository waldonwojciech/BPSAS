package pl.wojciechwaldon.bpsas.domain.repository.user.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {

    Optional<Company> findByEmail(String email);

    Optional<Company> findByCompanyName(String companyName);

    Optional<Set<Company>> findByCompanyNameStartingWith(String companyName);
}
