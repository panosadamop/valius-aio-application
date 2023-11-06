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

describe('BuyingCriteriaCategory e2e test', () => {
  const buyingCriteriaCategoryPageUrl = '/buying-criteria-category';
  const buyingCriteriaCategoryPageUrlPattern = new RegExp('/buying-criteria-category(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const buyingCriteriaCategorySample = { value: 'Dynamic hacking', language: 'GREEK' };

  let buyingCriteriaCategory;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/buying-criteria-categories+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/buying-criteria-categories').as('postEntityRequest');
    cy.intercept('DELETE', '/api/buying-criteria-categories/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (buyingCriteriaCategory) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/buying-criteria-categories/${buyingCriteriaCategory.id}`,
      }).then(() => {
        buyingCriteriaCategory = undefined;
      });
    }
  });

  it('BuyingCriteriaCategories menu should load BuyingCriteriaCategories page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('buying-criteria-category');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BuyingCriteriaCategory').should('exist');
    cy.url().should('match', buyingCriteriaCategoryPageUrlPattern);
  });

  describe('BuyingCriteriaCategory page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(buyingCriteriaCategoryPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BuyingCriteriaCategory page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/buying-criteria-category/new$'));
        cy.getEntityCreateUpdateHeading('BuyingCriteriaCategory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaCategoryPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/buying-criteria-categories',
          body: buyingCriteriaCategorySample,
        }).then(({ body }) => {
          buyingCriteriaCategory = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/buying-criteria-categories+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/buying-criteria-categories?page=0&size=20>; rel="last",<http://localhost/api/buying-criteria-categories?page=0&size=20>; rel="first"',
              },
              body: [buyingCriteriaCategory],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(buyingCriteriaCategoryPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BuyingCriteriaCategory page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('buyingCriteriaCategory');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaCategoryPageUrlPattern);
      });

      it('edit button click should load edit BuyingCriteriaCategory page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BuyingCriteriaCategory');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaCategoryPageUrlPattern);
      });

      it('edit button click should load edit BuyingCriteriaCategory page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BuyingCriteriaCategory');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaCategoryPageUrlPattern);
      });

      it('last delete button click should delete instance of BuyingCriteriaCategory', () => {
        cy.intercept('GET', '/api/buying-criteria-categories/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('buyingCriteriaCategory').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', buyingCriteriaCategoryPageUrlPattern);

        buyingCriteriaCategory = undefined;
      });
    });
  });

  describe('new BuyingCriteriaCategory page', () => {
    beforeEach(() => {
      cy.visit(`${buyingCriteriaCategoryPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BuyingCriteriaCategory');
    });

    it('should create an instance of BuyingCriteriaCategory', () => {
      cy.get(`[data-cy="value"]`).type('Federation Table matrix').should('have.value', 'Federation Table matrix');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        buyingCriteriaCategory = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', buyingCriteriaCategoryPageUrlPattern);
    });
  });
});
