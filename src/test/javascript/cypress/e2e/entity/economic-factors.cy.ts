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

describe('EconomicFactors e2e test', () => {
  const economicFactorsPageUrl = '/economic-factors';
  const economicFactorsPageUrlPattern = new RegExp('/economic-factors(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const economicFactorsSample = { value: 'Mobility e-markets integrate', language: 'ENGLISH' };

  let economicFactors;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/economic-factors+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/economic-factors').as('postEntityRequest');
    cy.intercept('DELETE', '/api/economic-factors/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (economicFactors) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/economic-factors/${economicFactors.id}`,
      }).then(() => {
        economicFactors = undefined;
      });
    }
  });

  it('EconomicFactors menu should load EconomicFactors page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('economic-factors');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('EconomicFactors').should('exist');
    cy.url().should('match', economicFactorsPageUrlPattern);
  });

  describe('EconomicFactors page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(economicFactorsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create EconomicFactors page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/economic-factors/new$'));
        cy.getEntityCreateUpdateHeading('EconomicFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', economicFactorsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/economic-factors',
          body: economicFactorsSample,
        }).then(({ body }) => {
          economicFactors = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/economic-factors+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/economic-factors?page=0&size=20>; rel="last",<http://localhost/api/economic-factors?page=0&size=20>; rel="first"',
              },
              body: [economicFactors],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(economicFactorsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details EconomicFactors page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('economicFactors');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', economicFactorsPageUrlPattern);
      });

      it('edit button click should load edit EconomicFactors page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EconomicFactors');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', economicFactorsPageUrlPattern);
      });

      it('edit button click should load edit EconomicFactors page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('EconomicFactors');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', economicFactorsPageUrlPattern);
      });

      it('last delete button click should delete instance of EconomicFactors', () => {
        cy.intercept('GET', '/api/economic-factors/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('economicFactors').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', economicFactorsPageUrlPattern);

        economicFactors = undefined;
      });
    });
  });

  describe('new EconomicFactors page', () => {
    beforeEach(() => {
      cy.visit(`${economicFactorsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('EconomicFactors');
    });

    it('should create an instance of EconomicFactors', () => {
      cy.get(`[data-cy="value"]`).type('Kuwaiti Solutions quantify').should('have.value', 'Kuwaiti Solutions quantify');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        economicFactors = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', economicFactorsPageUrlPattern);
    });
  });
});
