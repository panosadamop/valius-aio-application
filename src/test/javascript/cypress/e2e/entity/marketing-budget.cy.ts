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

describe('MarketingBudget e2e test', () => {
  const marketingBudgetPageUrl = '/marketing-budget';
  const marketingBudgetPageUrlPattern = new RegExp('/marketing-budget(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const marketingBudgetSample = { value: 'Unbranded', language: 'GREEK' };

  let marketingBudget;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/marketing-budgets+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/marketing-budgets').as('postEntityRequest');
    cy.intercept('DELETE', '/api/marketing-budgets/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (marketingBudget) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/marketing-budgets/${marketingBudget.id}`,
      }).then(() => {
        marketingBudget = undefined;
      });
    }
  });

  it('MarketingBudgets menu should load MarketingBudgets page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('marketing-budget');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MarketingBudget').should('exist');
    cy.url().should('match', marketingBudgetPageUrlPattern);
  });

  describe('MarketingBudget page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(marketingBudgetPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MarketingBudget page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/marketing-budget/new$'));
        cy.getEntityCreateUpdateHeading('MarketingBudget');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketingBudgetPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/marketing-budgets',
          body: marketingBudgetSample,
        }).then(({ body }) => {
          marketingBudget = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/marketing-budgets+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/marketing-budgets?page=0&size=20>; rel="last",<http://localhost/api/marketing-budgets?page=0&size=20>; rel="first"',
              },
              body: [marketingBudget],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(marketingBudgetPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MarketingBudget page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('marketingBudget');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketingBudgetPageUrlPattern);
      });

      it('edit button click should load edit MarketingBudget page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketingBudget');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketingBudgetPageUrlPattern);
      });

      it('edit button click should load edit MarketingBudget page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketingBudget');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketingBudgetPageUrlPattern);
      });

      it('last delete button click should delete instance of MarketingBudget', () => {
        cy.intercept('GET', '/api/marketing-budgets/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('marketingBudget').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketingBudgetPageUrlPattern);

        marketingBudget = undefined;
      });
    });
  });

  describe('new MarketingBudget page', () => {
    beforeEach(() => {
      cy.visit(`${marketingBudgetPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MarketingBudget');
    });

    it('should create an instance of MarketingBudget', () => {
      cy.get(`[data-cy="value"]`).type('Generic Hampshire Gloves').should('have.value', 'Generic Hampshire Gloves');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        marketingBudget = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', marketingBudgetPageUrlPattern);
    });
  });
});
