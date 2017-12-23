package pl.wojciechwaldon.bpsas.domain.repository.user.company;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CompanyRepository extends CrudRepository<Company, String> {

    public Optional<Company> findByEmail(String email);

    public Optional<Company> findByCompanyName(String companyName);

    public Optional<Set<Company>> findByCompanyNameStartingWith(String companyName);
}
