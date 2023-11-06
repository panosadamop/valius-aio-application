import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('NoOfEmployees e2e test', () => {
  const noOfEmployeesPageUrl = '/no-of-employees';
  const noOfEmployeesPageUrlPattern = new RegExp('/no-of-employees(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const noOfEmployeesSample = { value: 'deposit', language: 'GREEK' };

  let noOfEmployees;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/no-of-employees+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/no-of-employees').as('postEntityRequest');
    cy.intercept('DELETE', '/api/no-of-employees/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (noOfEmployees) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/no-of-employees/${noOfEmployees.id}`,
      }).then(() => {
        noOfEmployees = undefined;
      });
    }
  });

  it('NoOfEmployees menu should load NoOfEmployees page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('no-of-employees');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('NoOfEmployees').should('exist');
    cy.url().should('match', noOfEmployeesPageUrlPattern);
  });

  describe('NoOfEmployees page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(noOfEmployeesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create NoOfEmployees page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/no-of-employees/new$'));
        cy.getEntityCreateUpdateHeading('NoOfEmployees');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', noOfEmployeesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/no-of-employees',
          body: noOfEmployeesSample,
        }).then(({ body }) => {
          noOfEmployees = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/no-of-employees+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/no-of-employees?page=0&size=20>; rel="last",<http://localhost/api/no-of-employees?page=0&size=20>; rel="first"',
              },
              body: [noOfEmployees],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(noOfEmployeesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details NoOfEmployees page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('noOfEmployees');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', noOfEmployeesPageUrlPattern);
      });

      it('edit button click should load edit NoOfEmployees page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NoOfEmployees');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', noOfEmployeesPageUrlPattern);
      });

      it('edit button click should load edit NoOfEmployees page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NoOfEmployees');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', noOfEmployeesPageUrlPattern);
      });

      it('last delete button click should delete instance of NoOfEmployees', () => {
        cy.intercept('GET', '/api/no-of-employees/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('noOfEmployees').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', noOfEmployeesPageUrlPattern);

        noOfEmployees = undefined;
      });
    });
  });

  describe('new NoOfEmployees page', () => {
    beforeEach(() => {
      cy.visit(`${noOfEmployeesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('NoOfEmployees');
    });

    it('should create an instance of NoOfEmployees', () => {
      cy.get(`[data-cy="value"]`).type('Savings').should('have.value', 'Savings');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        noOfEmployees = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', noOfEmployeesPageUrlPattern);
    });
  });
});
