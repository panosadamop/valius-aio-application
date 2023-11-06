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

describe('CompetitiveFactors e2e test', () => {
  const competitiveFactorsPageUrl = '/competitive-factors';
  const competitiveFactorsPageUrlPattern = new RegExp('/competitive-factors(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const competitiveFactorsSample = { value: 'Metal Wooden', language: 'GREEK' };

  let competitiveFactors;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/competitive-factors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/competitive-factors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/competitive-factors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (competitiveFactors) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/competitive-factors/${competitiveFactors.id}`,
      }).then(() => {
        competitiveFactors = undefined;
      });
    }
  });

  it('CompetitiveFactors menu should load CompetitiveFactors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('competitive-factors');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CompetitiveFactors').should('exist');
    cy.url().should('match', competitiveFactorsPageUrlPattern);
  });

  describe('CompetitiveFactors page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(competitiveFactorsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CompetitiveFactors page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/competitive-factors/new$'));
        cy.getEntityCreateUpdateHeading('CompetitiveFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitiveFactorsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/competitive-factors',
          body: competitiveFactorsSample,
        }).then(({ body }) => {
          competitiveFactors = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/competitive-factors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/competitive-factors?page=0&size=20>; rel="last",<http://localhost/api/competitive-factors?page=0&size=20>; rel="first"',
              },
              body: [competitiveFactors],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(competitiveFactorsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CompetitiveFactors page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('competitiveFactors');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitiveFactorsPageUrlPattern);
      });

      it('edit button click should load edit CompetitiveFactors page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompetitiveFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitiveFactorsPageUrlPattern);
      });

      it('edit button click should load edit CompetitiveFactors page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompetitiveFactors');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitiveFactorsPageUrlPattern);
      });

      it('last delete button click should delete instance of CompetitiveFactors', () => {
        cy.intercept('GET', '/api/competitive-factors/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('competitiveFactors').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitiveFactorsPageUrlPattern);

        competitiveFactors = undefined;
      });
    });
  });

  describe('new CompetitiveFactors page', () => {
    beforeEach(() => {
      cy.visit(`${competitiveFactorsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CompetitiveFactors');
    });

    it('should create an instance of CompetitiveFactors', () => {
      cy.get(`[data-cy="value"]`).type('open-source uniform Cotton').should('have.value', 'open-source uniform Cotton');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('GREEK');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        competitiveFactors = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', competitiveFactorsPageUrlPattern);
    });
  });
});
