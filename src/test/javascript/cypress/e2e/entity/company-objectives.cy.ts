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

describe('CompanyObjectives e2e test', () => {
  const companyObjectivesPageUrl = '/company-objectives';
  const companyObjectivesPageUrlPattern = new RegExp('/company-objectives(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const companyObjectivesSample = { value: 'Paradigm Sausages', language: 'ENGLISH' };

  let companyObjectives;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/company-objectives+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/company-objectives').as('postEntityRequest');
    cy.intercept('DELETE', '/api/company-objectives/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (companyObjectives) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/company-objectives/${companyObjectives.id}`,
      }).then(() => {
        companyObjectives = undefined;
      });
    }
  });

  it('CompanyObjectives menu should load CompanyObjectives page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('company-objectives');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CompanyObjectives').should('exist');
    cy.url().should('match', companyObjectivesPageUrlPattern);
  });

  describe('CompanyObjectives page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(companyObjectivesPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CompanyObjectives page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/company-objectives/new$'));
        cy.getEntityCreateUpdateHeading('CompanyObjectives');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', companyObjectivesPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/company-objectives',
          body: companyObjectivesSample,
        }).then(({ body }) => {
          companyObjectives = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/company-objectives+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/company-objectives?page=0&size=20>; rel="last",<http://localhost/api/company-objectives?page=0&size=20>; rel="first"',
              },
              body: [companyObjectives],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(companyObjectivesPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CompanyObjectives page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('companyObjectives');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', companyObjectivesPageUrlPattern);
      });

      it('edit button click should load edit CompanyObjectives page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompanyObjectives');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', companyObjectivesPageUrlPattern);
      });

      it('edit button click should load edit CompanyObjectives page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompanyObjectives');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', companyObjectivesPageUrlPattern);
      });

      it('last delete button click should delete instance of CompanyObjectives', () => {
        cy.intercept('GET', '/api/company-objectives/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('companyObjectives').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', companyObjectivesPageUrlPattern);

        companyObjectives = undefined;
      });
    });
  });

  describe('new CompanyObjectives page', () => {
    beforeEach(() => {
      cy.visit(`${companyObjectivesPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CompanyObjectives');
    });

    it('should create an instance of CompanyObjectives', () => {
      cy.get(`[data-cy="value"]`).type('tangible Developer').should('have.value', 'tangible Developer');

      cy.get(`[data-cy="placeholder"]`).type('hack Rustic incremental').should('have.value', 'hack Rustic incremental');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        companyObjectives = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', companyObjectivesPageUrlPattern);
    });
  });
});
